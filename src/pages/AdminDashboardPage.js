// RUTA: src/pages/AdminDashboardPage.js

import React from 'react';
import { useAuth } from '../context/AuthContext';
// --- ¡LA LÍNEA MÁS IMPORTANTE! ---
// Asegúrate de que esta línea esté presente y sea correcta.
import { useNavigate } from 'react-router-dom';

// Ajusta la ruta a tu componente si es necesario
import ImportarPaciente from '../components/ImportarPaciente';

const AdminDashboardPage = () => {
    const { user, logout } = useAuth();
    // Ahora 'useNavigate' está definido porque lo importamos arriba
    const navigate = useNavigate();

    const menuOptions = [
        {
            title: 'Gestionar Profesionales',
            description: 'Añadir, editar o eliminar cuentas de profesionales de la salud.',
            path: `/${user?.tenant_id}/admin/profesionales`,
            icon: '👥'
        },
        {
            title: 'Configuración',
            description: 'Ajustar parámetros y personalización de la clínica.',
            path: `/${user?.tenant_id}/admin/settings`,
            icon: '⚙️'
        },
        {
            title: 'Reportes y Estadísticas',
            description: 'Visualizar datos y métricas de uso (próximamente).',
            path: '#',
            icon: '📊'
        },
        {
            title: 'Editar Profesional por Email',
            description: 'Buscar un profesional por su correo y modificar sus datos.',
            path: `/${user?.tenant_id}/admin/profesionales/editar`,
            icon: '✏️'
        },
    ];

    const handleCardClick = (path) => {
        if (path !== '#') {
            navigate(path);
        }
    };

    return (
        <div style={styles.container}>
            <header style={styles.header}>
                <div>
                    <h1>Dashboard de Administración</h1>
                    <p>Bienvenido, <strong>{user?.sub}</strong> (Tenant: {user?.tenant_id})</p>
                </div>
                <button onClick={logout} style={styles.logoutButton}>Cerrar Sesión</button>
            </header>

            <div style={{ padding: '0 40px 40px 40px' }}>
                <ImportarPaciente />
            </div>

            <div style={styles.menuGrid}>
                {menuOptions.map((option, index) => (
                    <div
                        key={index}
                        style={option.path === '#' ? {...styles.card, ...styles.cardDisabled} : styles.card}
                        onClick={() => handleCardClick(option.path)}
                    >
                        <div style={styles.cardIcon}>{option.icon}</div>
                        <h3 style={styles.cardTitle}>{option.title}</h3>
                        <p style={styles.cardDescription}>{option.description}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

// ... Estilos sin cambios ...
const styles = {
    container: { fontFamily: 'sans-serif', backgroundColor: '#f4f7f9', minHeight: '100vh' },
    header: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '20px 40px', backgroundColor: 'white', borderBottom: '1px solid #ddd' },
    logoutButton: { padding: '8px 12px', border: 'none', backgroundColor: '#dc3545', color: 'white', borderRadius: '5px', cursor: 'pointer' },
    menuGrid: { display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '30px', padding: '0 40px 40px 40px' },
    card: { backgroundColor: 'white', padding: '25px', borderRadius: '8px', boxShadow: '0 4px 8px rgba(0,0,0,0.1)', cursor: 'pointer', transition: 'transform 0.2s, box-shadow 0.2s', borderLeft: '5px solid #007bff' },
    cardDisabled: { cursor: 'not-allowed', backgroundColor: '#e9ecef', borderLeft: '5px solid #6c757d' },
    cardIcon: { fontSize: '2.5rem', marginBottom: '15px' },
    cardTitle: { margin: '0 0 10px 0', color: '#333' },
    cardDescription: { margin: 0, color: '#666', fontSize: '0.9rem' },
};

export default AdminDashboardPage;