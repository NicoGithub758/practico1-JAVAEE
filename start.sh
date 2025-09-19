#!/bin/bash

# Iniciar WildFly en segundo plano
echo "==> Iniciando WildFly en segundo plano..."
/opt/wildfly/bin/standalone.sh -b 0.0.0.0 &
WILDFLY_PID=$!

echo "==> WildFly iniciado (PID: ${WILDFLY_PID}). Esperando a que el despliegue de '/Laboratorio' finalice..."

# Bucle de espera inteligente: Comprueba cada 3 segundos si la app está lista
# Se rinde después de 90 segundos (30 intentos)
for i in {1..30}; do
    if curl -s -f http://localhost:8080/Laboratorio > /dev/null; then
        echo "==> ¡DESPLIEGUE COMPLETADO Y EXITOSO! La aplicación esta lista."
        wait $WILDFLY_PID
        exit $?
    fi
    echo "    ... intento $i, la aplicacion aun no esta lista. Reintentando en 3 segundos..."
    sleep 3
done

echo "==> ERROR FATAL: La aplicacion no arranco en 90 segundos."
kill $WILDFLY_PID
exit 1