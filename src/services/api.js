// RUTA: src/services/api.js

import axios from 'axios';

const API_URL = 'http://localhost:8082';

const apiClient = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const setAuthToken = (token) => {
  if (token) {
    apiClient.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  } else {
    delete apiClient.defaults.headers.common['Authorization'];
  }
};


// ===========================================
// === Funciones de Autenticación y Perfil ===
// ===========================================

export const login = (tenantId, username, password, userType) => {
  const loginEndpoint = userType === 'admin' ? 'admin' : 'profesional';
  return apiClient.post(`/${tenantId}/api/auth/login/${loginEndpoint}`, { username, password });
};

// Obtiene los datos del perfil del profesional autenticado
export const getOwnProfile = (tenantId) => apiClient.get(`/${tenantId}/api/auth/perfil`);

// Actualiza los datos del perfil del profesional autenticado
export const updateOwnProfile = (tenantId, data) => apiClient.put(`/${tenantId}/api/auth/perfil`, data);


// ===================================================
// === Funciones de Administración de Profesionales ===
// ===================================================

// Obtiene la lista de todos los profesionales
export const getProfesionales = (tenantId) => apiClient.get(`/${tenantId}/api/admin/profesionales`);

// Crea un nuevo profesional
export const createProfesional = (tenantId, data) => apiClient.post(`/${tenantId}/api/admin/profesionales`, data);

// Actualiza un profesional por su ID
export const updateProfesional = (tenantId, id, data) => apiClient.put(`/${tenantId}/api/admin/profesionales/${id}`, data);

// Elimina un profesional por su ID
export const deleteProfesional = (tenantId, id) => apiClient.delete(`/${tenantId}/api/admin/profesionales/${id}`);

// Busca un profesional por su email
export const getProfesionalByEmail = (tenantId, email) => apiClient.get(`/${tenantId}/api/admin/profesionales/email/${email}`);


export default apiClient;