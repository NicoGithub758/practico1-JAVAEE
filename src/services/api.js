// RUTA: src/services/api.js

import axios from 'axios';

// --- CAMBIO ---: La URL base es solo el host.
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

// --- CAMBIO ---: Las funciones ahora necesitan el tenantId para construir la URL correcta.

// Función de login (usada por AuthContext)
export const login = (tenantId, username, password, userType) => {
  const loginEndpoint = userType === 'admin' ? 'admin' : 'profesional';
  return apiClient.post(`/${tenantId}/api/auth/login/${loginEndpoint}`, { username, password });
};


// Funciones para el CRUD de Profesionales
export const getProfesionales = (tenantId) => apiClient.get(`/${tenantId}/api/admin/profesionales`);
export const createProfesional = (tenantId, data) => apiClient.post(`/${tenantId}/api/admin/profesionales`, data);
export const updateProfesional = (tenantId, id, data) => apiClient.put(`/${tenantId}/api/admin/profesionales/${id}`, data);
export const deleteProfesional = (tenantId, id) => apiClient.delete(`/${tenantId}/api/admin/profesionales/${id}`);

export default apiClient;