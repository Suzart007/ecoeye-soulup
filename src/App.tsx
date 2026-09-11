// commit 10/29 (Guilherme): feat(routes) - implementa roteamento da SPA com React Router DOM
import { Route, Routes } from 'react-router-dom'
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
import NotFound from './pages/NotFound'

export default function App() {
  return (
    <>
      <ScrollToTop />
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="/sobre" element={<Sobre />} />
          <Route path="/solucao" element={<Solucao />} />
          <Route path="/experiencia" element={<Experiencia />} />
          <Route path="/faq" element={<Faq />} />
          <Route path="/contato" element={<Contato />} />
          <Route path="/integrantes" element={<Integrantes />} />
          <Route path="/integrantes/:id" element={<IntegranteDetalhe />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  )
}
