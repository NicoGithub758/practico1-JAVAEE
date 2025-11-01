// RUTA A CREAR: src/pages/AdminSettingsPage.js

import React from 'react';
import { Link, useParams } from 'react-router-dom';

const AdminSettingsPage = () => {
    const { tenantId } = useParams();

    return (
        <div style={styles.container}>
            <div style={styles.header}>
                <h1>Configuración de la Clínica</h1>
                {/* Enlace para volver al menú principal del admin */}
                <Link to={`/${tenantId}/admin/dashboard`}>Volver al Dashboard</Link>
            </div>
            <div style={styles.content}>
                <p>Esta sección está en construcción.</p>
                <p>Aquí podrás configurar los parámetros específicos de tu organización, como el tema visual, notificaciones, etc.</p>
            </div>
        </div>
    );
};

const styles = {
    container: { padding: '20px', fontFamily: 'sans-serif', maxWidth: '1000px', margin: 'auto' },
    header: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px', borderBottom: '1px solid #ccc', paddingBottom: '10px' },
    content: { padding: '20px', backgroundColor: '#fff', borderRadius: '8px', boxShadow: '0 2px 5px rgba(0,0,0,0.1)' },
};

export default AdminSettingsPage;