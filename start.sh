#!/bin/bash

# Iniciar WildFly en segundo plano
echo "==> Iniciando WildFly en segundo plano..."
/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 &

# Guardar el ID del proceso de WildFly
WILDFLY_PID=$!

echo "==> WildFly iniciado con PID: ${WILDFLY_PID}"
echo "==> Esperando a que la aplicacion '/Laboratorio' este disponible (esto puede tardar hasta 60 segundos)..."

# Bucle de espera: Intentar acceder a la URL hasta que devuelva un 200 OK
# Usamos un timeout de 60 segundos en total
for i in {1..12}; do
    # Usamos curl para comprobar. El flag -f hace que falle si el HTTP status no es 2xx.
    if curl -s -f http://localhost:8080/Laboratorio > /dev/null; then
        echo "==> ¡La aplicacion esta lista!"
        # Traer el proceso de WildFly al primer plano para mantener el contenedor vivo
        wait $WILDFLY_PID
        exit $?
    fi
    echo "    ... intento $i, la aplicacion aun no responde. Esperando 5 segundos..."
    sleep 5
done

echo "==> ERROR: La aplicacion no arranco en el tiempo esperado."
# Si el bucle termina, algo salió mal. Matamos el proceso para que el contenedor falle.
kill $WILDFLY_PID
exit 1