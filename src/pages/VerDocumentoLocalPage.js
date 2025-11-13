import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { getDocumentoLocal } from '../services/api';

const VerDocumentoLocalPage = () => {
    const { idExternaDoc } = useParams(); // Obtiene el ID del doc desde la URL
    const { user } = useAuth();
    const [documento, setDocumento] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchDocumento = async () => {
            try {
                const response = await getDocumentoLocal(user.tenant_id, idExternaDoc);
                setDocumento(response.data);
            } catch (err) {
                setError('No se pudo cargar el documento local.');
            } finally {
                setLoading(false);
            }
        };

        if (user && idExternaDoc) {
            fetchDocumento();
        }
    }, [user, idExternaDoc]);

    return (
        <div style={styles.container}>
            <div style={styles.header}>
                <h1>Detalle de Documento Clínico Local</h1>
                <Link to={`/${user?.tenant_id}/historia-paciente`}>Volver a la Búsqueda</Link>
            </div>
            <div style={styles.card}>
                {loading && <p>Cargando documento...</p>}
                {error && <p style={styles.error}>{error}</p>}
                {documento && (
                    <pre style={styles.preformatted}>
                        {JSON.stringify(documento, null, 2)}
                    </pre>
                )}
            </div>
        </div>
    );
};

const styles = {
    container: { padding: '20px', fontFamily: 'sans-serif', maxWidth: '900px', margin: 'auto' },
    header: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '30px', borderBottom: '1px solid #ccc', paddingBottom: '15px' },
    card: { padding: '25px', backgroundColor: '#f8f9fa', borderRadius: '8px', boxShadow: '0 4px 8px rgba(0,0,0,0.05)' },
    error: { color: 'red' },
    preformatted: { whiteSpace: 'pre-wrap', wordBreak: 'break-all', fontSize: '0.9rem' }
};

export default VerDocumentoLocalPage;