// RUTA: src/pages/ManageProfesionalesPage.js

import React, { useState, useEffect } from 'react';
import { getProfesionales, deleteProfesional, createProfesional } from '../services/api';
import { Link, useParams } from 'react-router-dom';

// Estado inicial para el formulario, facilita resetearlo.
// No incluye 'rol' ni 'estado', ya que el backend los gestiona por defecto.
const initialFormState = {
    username: '',
    password: '',
    nombre: '',
    apellido: '',
    email: '',
    especializacion: ''
};

const ManageProfesionalesPage = () => {
    const { tenantId } = useParams();

    // Estados para la lista de profesionales
    const [profesionales, setProfesionales] = useState([]);
    const [loading, setLoading] = useState(true);

    // Estados para el formulario y su visibilidad
    const [isFormVisible, setIsFormVisible] = useState(false);
    const [newProfesionalData, setNewProfesionalData] = useState(initialFormState);
    const [isSubmitting, setIsSubmitting] = useState(false);

    // Estado para manejar errores (de fetch, creación, etc.)
    const [error, setError] = useState('');

    // Función para cargar la lista de profesionales desde el backend
    const fetchProfesionales = async () => {
        if (!tenantId) return;
        try {
            setLoading(true);
            const response = await getProfesionales(tenantId);
            setProfesionales(response.data);
            setError(''); // Limpia errores previos si la carga es exitosa
        } catch (err) {
            setError('No se pudieron cargar los profesionales.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    // Carga los profesionales cuando el componente se monta por primera vez
    useEffect(() => {
        fetchProfesionales();
    }, [tenantId]);

    // Actualiza el estado del formulario cada vez que el usuario escribe en un campo
    const handleFormChange = (e) => {
        const { name, value } = e.target;
        setNewProfesionalData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    // Envía los datos del nuevo profesional al backend
    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsSubmitting(true);
        setError('');
        try {
            await createProfesional(tenantId, newProfesionalData);
            alert('¡Profesional creado con éxito!');
            setIsFormVisible(false); // Oculta el formulario
            setNewProfesionalData(initialFormState); // Resetea los campos del formulario
            fetchProfesionales(); // Vuelve a cargar la lista para mostrar al nuevo profesional
        } catch (err) {
            setError(err.response?.data?.message || 'Error al crear el profesional. El username o email podrían ya existir.');
            console.error(err);
        } finally {
            setIsSubmitting(false);
        }
    };

    // Maneja la eliminación de un profesional
    const handleDelete = async (profesionalId) => {
        if (window.confirm('¿Estás seguro de que quieres eliminar a este profesional?')) {
            try {
                await deleteProfesional(tenantId, profesionalId);
                fetchProfesionales(); // Recarga la lista para que el profesional eliminado desaparezca
            } catch (err) {
                setError('Error al eliminar el profesional.');
                console.error(err);
            }
        }
    };

    return (
        <div style={styles.container}>
            <div style={styles.header}>
                <h1>Gestión de Profesionales</h1>
                <Link to={`/${tenantId}/admin/dashboard`}>Volver al Dashboard</Link>
            </div>

            {!isFormVisible && (
                <button style={styles.buttonAdd} onClick={() => setIsFormVisible(true)}>
                    Añadir Nuevo Profesional
                </button>
            )}

            {isFormVisible && (
                <div style={styles.formContainer}>
                    <h2>Nuevo Profesional</h2>
                    <form onSubmit={handleSubmit}>
                        <div style={styles.formGrid}>
                            <input name="nombre" value={newProfesionalData.nombre} onChange={handleFormChange} placeholder="Nombre" required style={styles.input} />
                            <input name="apellido" value={newProfesionalData.apellido} onChange={handleFormChange} placeholder="Apellido" required style={styles.input} />
                            <input name="username" value={newProfesionalData.username} onChange={handleFormChange} placeholder="Username (para login)" required style={styles.input} />
                            <input name="password" type="password" value={newProfesionalData.password} onChange={handleFormChange} placeholder="Contraseña" required style={styles.input} />
                            <input name="email" type="email" value={newProfesionalData.email} onChange={handleFormChange} placeholder="Email" required style={styles.input} />
                            <input name="especializacion" value={newProfesionalData.especializacion} onChange={handleFormChange} placeholder="Especialización" style={styles.input} />
                        </div>
                        {error && <p style={{ color: 'red', gridColumn: '1 / -1' }}>{error}</p>}
                        <div style={styles.formActions}>
                            <button type="submit" style={styles.buttonSave} disabled={isSubmitting}>
                                {isSubmitting ? 'Guardando...' : 'Guardar Profesional'}
                            </button>
                            <button type="button" style={styles.buttonCancel} onClick={() => setIsFormVisible(false)}>
                                Cancelar
                            </button>
                        </div>
                    </form>
                </div>
            )}

            <hr style={{margin: '30px 0'}}/>

            <h2>Lista de Profesionales</h2>
            {loading ? <p>Cargando lista...</p> : (
                <table style={styles.table}>
                    <thead>
                    <tr>
                        <th style={styles.th}>Username</th>
                        <th style={styles.th}>Nombre Completo</th>
                        <th style={styles.th}>Email</th>
                        <th style={styles.th}>Rol</th>
                        <th style={styles.th}>Estado</th>
                        <th style={styles.th}>Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    {profesionales.map(p => (
                        <tr key={p.id}>
                            <td style={styles.td}>{p.username}</td>
                            <td style={styles.td}>{p.nombre} {p.apellido}</td>
                            <td style={styles.td}>{p.email}</td>
                            <td style={styles.td}>{p.rol}</td>
                            <td style={styles.td}>
                                    <span style={p.estado === 'Activo' ? styles.badgeActive : styles.badgeInactive}>
                                        {p.estado}
                                    </span>
                            </td>
                            <td style={styles.td}>
                                <button style={styles.buttonEdit}>Editar</button>
                                <button onClick={() => handleDelete(p.id)} style={styles.buttonDelete}>Eliminar</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

// Estilos
const styles = {
    container: { padding: '20px', fontFamily: 'sans-serif', maxWidth: '1000px', margin: 'auto' },
    header: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px', borderBottom: '1px solid #ccc', paddingBottom: '10px' },
    table: { width: '100%', marginTop: '20px', borderCollapse: 'collapse', boxShadow: '0 2px 5px rgba(0,0,0,0.1)' },
    th: { padding: '12px', textAlign: 'left', borderBottom: '2px solid #ddd', background: '#f8f9fa', fontWeight: 'bold' },
    td: { padding: '12px', textAlign: 'left', borderBottom: '1px solid #ddd' },
    buttonAdd: { padding: '10px 15px', backgroundColor: '#28a745', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer', marginBottom: '20px', fontSize: '1em' },
    buttonEdit: { marginRight: '5px', padding: '5px 10px', backgroundColor: '#007bff', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' },
    buttonDelete: { padding: '5px 10px', backgroundColor: '#dc3545', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' },
    formContainer: { padding: '20px', backgroundColor: '#f8f9fa', border: '1px solid #dee2e6', borderRadius: '8px', margin: '20px 0' },
    formGrid: { display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px', marginBottom: '15px' },
    input: { width: '100%', padding: '10px', boxSizing: 'border-box', borderRadius: '5px', border: '1px solid #ccc' },
    formActions: { display: 'flex', justifyContent: 'flex-end', gap: '10px', marginTop: '10px' },
    buttonSave: { padding: '10px 15px', backgroundColor: '#007bff', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' },
    buttonCancel: { padding: '10px 15px', backgroundColor: '#6c757d', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' },
    badgeActive: { backgroundColor: '#d1e7dd', color: '#0f5132', padding: '4px 8px', borderRadius: '10px', fontSize: '0.8rem', fontWeight: 'bold' },
    badgeInactive: { backgroundColor: '#e2e3e5', color: '#495057', padding: '4px 8px', borderRadius: '10px', fontSize: '0.8rem', fontWeight: 'bold' },
};

export default ManageProfesionalesPage;