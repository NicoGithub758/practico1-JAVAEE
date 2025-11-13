import React, { useState, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { getTenantConfig, updateTenantConfig } from '../services/api';

const AdminSettingsPage = () => {
    const { tenantId } = useParams();
    const [config, setConfig] = useState({
        tituloPrincipal: '',
        colorPrimario: '#000000',
        colorFondo: '#ffffff',
        logoUrl: '' // Almacenará el string Base64 de la imagen
    });
    const [loading, setLoading] = useState(true);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    // Al cargar la página, obtiene la configuración actual para rellenar el formulario.
    useEffect(() => {
        const fetchConfig = async () => {
            try {
                const response = await getTenantConfig(tenantId);
                setConfig(response.data);
            } catch (err) {
                setError('No se pudo cargar la configuración actual.');
            } finally {
                setLoading(false);
            }
        };
        fetchConfig();
    }, [tenantId]);

    // Maneja los cambios en los campos de texto y color.
    const handleChange = (e) => {
        const { name, value } = e.target;
        setConfig(prev => ({ ...prev, [name]: value }));
    };

    // Maneja la selección de un archivo de imagen.
    const handleFileChange = (e) => {
        const file = e.target.files[0];
        if (!file) return;

        // Validaciones de seguridad y tamaño.
        if (file.size > 102400) { // Límite de 100 KB
            setError("El archivo es muy grande. El límite es 100 KB.");
            return;
        }
        if (!['image/png', 'image/jpeg', 'image/svg+xml'].includes(file.type)) {
            setError("Formato de archivo no válido. Use PNG, JPG o SVG.");
            return;
        }

        // Convierte la imagen a un string Base64 para guardarla.
        const reader = new FileReader();
        reader.onload = () => {
            setConfig(prev => ({ ...prev, logoUrl: reader.result }));
            setError('');
        };
        reader.onerror = () => {
            setError("No se pudo leer el archivo.");
        };
        reader.readAsDataURL(file);
    };

    // Se ejecuta al enviar el formulario.
    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsSubmitting(true);
        setError('');
        setSuccess('');
        try {
            // Prepara el objeto con todos los datos del formulario.
            const updateData = {
                tituloPrincipal: config.tituloPrincipal,
                colorPrimario: config.colorPrimario,
                colorFondo: config.colorFondo,
                logoUrl: config.logoUrl
            };
            // Llama a la función de la API para guardar los cambios.
            await updateTenantConfig(tenantId, updateData);
            setSuccess('¡Configuración guardada! La página se recargará para aplicar los cambios.');

            // Recarga la página para que el ThemeContext se actualice en toda la app.
            setTimeout(() => window.location.reload(), 2000);
        } catch (err) {
            setError(err.response?.data?.message || 'Error al guardar la configuración.');
        } finally {
            setIsSubmitting(false);
        }
    };

    if (loading) {
        return <div style={styles.container}>Cargando configuración...</div>;
    }

    return (
        <div style={styles.container}>
            <div style={styles.header}>
                <h1>Configuración de Apariencia</h1>
                <Link to={`/${tenantId}/admin/dashboard`}>Volver al Dashboard</Link>
            </div>
            <div style={styles.card}>
                <form onSubmit={handleSubmit}>
                    {/* --- Campo para editar el TÍTULO --- */}
                    <div style={styles.formGroup}>
                        <label style={styles.label}>Título Principal</label>
                        <input name="tituloPrincipal" value={config.tituloPrincipal || ''} onChange={handleChange} style={styles.input} />
                    </div>

                    {/* --- Campo para SUBIR EL LOGO --- */}
                    <div style={styles.formGroup}>
                        <label style={styles.label}>Logo de la Clínica</label>
                        {config.logoUrl && (
                            <div style={styles.previewContainer}>
                                <p>Vista Previa:</p>
                                <img src={config.logoUrl} alt="Vista previa del logo" style={styles.logoPreview} />
                            </div>
                        )}
                        <input type="file" accept="image/png, image/jpeg, image/svg+xml" onChange={handleFileChange} style={styles.fileInput} />
                        <small>Límite: 100 KB. Formatos: PNG, JPG, SVG.</small>
                    </div>

                    {/* --- Campos para editar los COLORES --- */}
                    <div style={styles.colorGrid}>
                        <div style={styles.formGroup}>
                            <label style={styles.label}>Color Primario (Botones)</label>
                            <div style={styles.colorPickerContainer}>
                                <input name="colorPrimario" type="color" value={config.colorPrimario || '#000000'} onChange={handleChange} style={styles.colorInput} />
                                <span style={styles.colorValue}>{config.colorPrimario}</span>
                            </div>
                        </div>
                        <div style={styles.formGroup}>
                            <label style={styles.label}>Color de Fondo (Login)</label>
                            <div style={styles.colorPickerContainer}>
                                <input name="colorFondo" type="color" value={config.colorFondo || '#ffffff'} onChange={handleChange} style={styles.colorInput} />
                                <span style={styles.colorValue}>{config.colorFondo}</span>
                            </div>
                        </div>
                    </div>

                    {error && <p style={styles.error}>{error}</p>}
                    {success && <p style={styles.success}>{success}</p>}

                    <div style={styles.formActions}>
                        <button type="submit" style={styles.buttonSave} disabled={isSubmitting}>
                            {isSubmitting ? 'Guardando...' : 'Guardar Cambios'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

// Estilos para que el formulario se vea bien
const styles = {
    container: { padding: '20px', fontFamily: 'sans-serif', maxWidth: '800px', margin: 'auto', backgroundColor: '#f4f7f9', minHeight: '100vh' },
    header: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '30px', borderBottom: '1px solid #ccc', paddingBottom: '15px' },
    card: { padding: '30px', backgroundColor: '#fff', borderRadius: '8px', boxShadow: '0 4px 8px rgba(0,0,0,0.05)' },
    formGroup: { marginBottom: '25px' },
    label: { display: 'block', marginBottom: '8px', fontWeight: 'bold', color: '#555' },
    input: { width: '100%', padding: '12px', boxSizing: 'border-box', borderRadius: '5px', border: '1px solid #ccc' },
    fileInput: { width: '100%', marginTop: '10px', padding: '10px', border: '1px solid #ccc', borderRadius: '5px' },
    previewContainer: { padding: '10px', border: '1px dashed #ccc', borderRadius: '5px', marginBottom: '10px', textAlign: 'center' },
    logoPreview: { maxWidth: '150px', maxHeight: '70px', border: '1px solid #eee' },
    colorGrid: { display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '30px' },
    colorPickerContainer: { display: 'flex', alignItems: 'center', gap: '15px' },
    colorInput: { border: '1px solid #ccc', padding: '0', width: '50px', height: '50px', cursor: 'pointer', borderRadius: '5px' },
    colorValue: { fontFamily: 'monospace', fontSize: '1.1em' },
    error: { color: '#dc3545', backgroundColor: '#f8d7da', border: '1px solid #f5c2c7', padding: '10px', borderRadius: '5px', textAlign: 'center' },
    success: { color: '#0f5132', backgroundColor: '#d1e7dd', border: '1px solid #badbcc', padding: '10px', borderRadius: '5px', textAlign: 'center' },
    formActions: { display: 'flex', justifyContent: 'flex-end', marginTop: '20px' },
    buttonSave: { padding: '12px 25px', backgroundColor: '#28a745', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer', fontSize: '1em', fontWeight: 'bold' },
};

export default AdminSettingsPage;