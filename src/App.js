// RUTA: src/App.js

import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';

// Importaciones de páginas y componentes
import ProfesionalLoginPage from './pages/ProfesionalLoginPage';
import AdminLoginPage from './pages/AdminLoginPage';
import DashboardPage from './pages/DashboardPage';
// --- CORRECCIÓN AQUÍ ---
// Antes: import AdminDashboardPage from './pages.AdminDashboardPage';
// Ahora:
import AdminDashboardPage from './pages/AdminDashboardPage';

import ManageProfesionalesPage from './pages/ManageProfesionalesPage';
import AdminSettingsPage from './pages/AdminSettingsPage';

import TenantLayout from './components/TenantLayout';
import ProtectedRoute from './components/ProtectedRoute';
import AdminRoute from './components/AdminRoute';

function App() {
    return (
        <BrowserRouter>
            <AuthProvider>
                <Routes>
                    <Route path="/:tenantId" element={<TenantLayout />}>

                        <Route path="login" element={<ProfesionalLoginPage />} />
                        <Route path="admin/login" element={<AdminLoginPage />} />

                        <Route element={<ProtectedRoute />}>
                            <Route path="dashboard" element={<DashboardPage />} />
                        </Route>

                        <Route element={<AdminRoute />}>
                            <Route path="admin/dashboard" element={<AdminDashboardPage />} />
                            <Route path="admin/profesionales" element={<ManageProfesionalesPage />} />
                            <Route path="admin/settings" element={<AdminSettingsPage />} />
                        </Route>
                    </Route>

                    <Route path="*" element={<h2 style={{textAlign: 'center', marginTop: '50px'}}>Bienvenido. Por favor, accede a través de la URL de tu organización.</h2>} />
                </Routes>
            </AuthProvider>
        </BrowserRouter>
    );
}

export default App;