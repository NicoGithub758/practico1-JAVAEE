// RUTA: src/pages/DashboardPage.js

import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import apiClient from '../services/api';
import { Link } from 'react-router-dom';
import SolicitarAcceso from '../components/SolicitarAcceso';
import GestionarPacientes from '../components/GestionarPacientes'; // --- IMPORTAR NUEVO COMPONENTE ---

const DashboardPage = () => {
    const { user, logout } = useAuth();
    const [documento, setDocumento] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [docId, setDocId] = useState('DOC-TENANT_A-1234abcd');

    const fetchDocumento = async () => {
        if (!user) return;
        setLoading(true);
        setError('');
        try {
            const tenantId = user.tenant_id;
            const response = await apiClient.get(`/${tenantId}/api/documentos/${docId}`);
            setDocumento(response.data);
        } catch (err) {
            setDocumento(null);
            setError(`No se pudo cargar el documento. (Error: ${err.response?.status || 'desconocido'})`);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={{ padding: '20px', fontFamily: 'sans-serif', maxWidth: '900px', margin: 'auto' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', borderBottom: '1px solid #eee', paddingBottom: '10px' }}>
                <h1>Dashboard Profesional</h1>
                <div>
                    <Link to={`/${user?.tenant_id}/perfil`} style={{ marginRight: '20px', textDecoration: 'none', color: '#007bff', fontWeight: 'bold' }}>
                        Mi Perfil
                    </Link>
                    <button onClick={logout} style={{padding: '8px 12px', border: 'none', backgroundColor: '#dc3545', color: 'white', borderRadius: '5px', cursor: 'pointer'}}>
                        Cerrar Sesión
                    </button>
                </div>
            </div>

            <p style={{ marginTop: '20px' }}>
                Bienvenido, <strong>{user?.sub}</strong> (Tenant: {user?.tenant_id}).
            </p>

            <div style={{ marginTop: '30px', padding: '20px', backgroundColor: '#e9f5ff', borderRadius: '8px', textAlign: 'center' }}>
                <h2>Acciones Rápidas</h2>
                <Link to={`/${user?.tenant_id}/crear-historia`}>
                    <button style={{ padding: '12px 20px', fontSize: '1.1em', border: 'none', backgroundColor: '#28a745', color: 'white', borderRadius: '5px', cursor: 'pointer' }}>
                        ➕ Crear Nueva Historia Clínica
                    </button>
                </Link>
            </div>

            <SolicitarAcceso />

            {/* --- INTEGRACIÓN DEL NUEVO COMPONENTE --- */}
            <GestionarPacientes />

            <div style={{ marginTop: '30px', padding: '20px', backgroundColor: '#f8f9fa', borderRadius: '8px' }}>
                <h3>Consultar Documento Clínico (Local)</h3>
                <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
                    <label>ID del Documento: </label>
                    <input type="text" value={docId} onChange={(e) => setDocId(e.target.value)} style={{ padding: '8px', borderRadius: '5px', border: '1px solid #ccc' }} />
                    <button onClick={fetchDocumento} disabled={loading} style={{ padding: '8px 12px', border: 'none', backgroundColor: '#007bff', color: 'white', borderRadius: '5px', cursor: 'pointer' }}>
                        {loading ? 'Buscando...' : 'Buscar'}
                    </button>
                </div>

                {error && <p style={{color: 'red', marginTop: '10px'}}>{error}</p>}

                {documento && (
                    <div style={{marginTop: '20px', border: '1px solid #ccc', padding: '15px', borderRadius: '5px', backgroundColor: 'white'}}>
                        <h4>Resultado Encontrado:</h4>
                        <pre style={{ whiteSpace: 'pre-wrap', wordBreak: 'break-all' }}>
                            {JSON.stringify(documento, null, 2)}
                        </pre>
                    </div>
                )}
            </div>
        </div>
    );
};

export default DashboardPage;