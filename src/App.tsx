// commit 10/29 (Guilherme): feat(routes) - implementa roteamento da SPA com React Router DOM
import { Navigate, Route, Routes } from 'react-router-dom'
import ScrollToTop from './components/common/ScrollToTop'
import Layout from './components/layout/Layout'
import Home from './pages/Home'
import Sobre from './pages/Sobre'
import Solucao from './pages/Solucao'
import Experiencia from './pages/Experiencia'
import Faq from './pages/Faq'
import Contato from './pages/Contato'
import Integrantes from './pages/Integrantes'
import IntegranteDetalhe from './pages/IntegranteDetalhe'
import Missoes from './pages/Missoes'
import MissaoDetalhe from './pages/MissaoDetalhe'
import MissaoFormPage from './pages/MissaoFormPage'
import Usuarios from './pages/Usuarios'
import UsuarioDetalhe from './pages/UsuarioDetalhe'
import UsuarioFormPage from './pages/UsuarioFormPage'
import NotFound from './pages/NotFound'

export default function App() {
  return (
    <>
      <ScrollToTop />
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="/home" element={<Navigate to="/" replace />} />
          <Route path="/sobre" element={<Sobre />} />
          <Route path="/solucao" element={<Solucao />} />
          <Route path="/experiencia" element={<Experiencia />} />
          <Route path="/faq" element={<Faq />} />
          <Route path="/contato" element={<Contato />} />
          <Route path="/integrantes" element={<Integrantes />} />
          <Route path="/integrantes/:id" element={<IntegranteDetalhe />} />

          {/* Rotas integradas à API Java (listagem, detalhe, cadastro e edição) */}
          <Route path="/missoes" element={<Missoes />} />
          <Route path="/missoes/nova" element={<MissaoFormPage />} />
          <Route path="/missoes/:id" element={<MissaoDetalhe />} />
          <Route path="/missoes/:id/editar" element={<MissaoFormPage />} />
          <Route path="/usuarios" element={<Usuarios />} />
          <Route path="/usuarios/novo" element={<UsuarioFormPage />} />
          <Route path="/usuarios/:id" element={<UsuarioDetalhe />} />
          <Route path="/usuarios/:id/editar" element={<UsuarioFormPage />} />

          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  )
}
