// commit 15/29 (Kaio): feat(layout) - cria Footer com links do rodapé
import { Link } from 'react-router-dom'

export default function Footer() {
  const year = new Date().getFullYear()

  return (
    <footer className="bg-primary-dark text-white">
      <div className="max-w-7xl mx-auto px-4 md:px-8 py-12">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div>
            <h3 className="font-heading font-bold text-lg mb-2">SoulUp</h3>
            <p className="text-sm text-secondary leading-relaxed">
              Uma plataforma da Prospera que conecta engajamento digital a benefícios reais e impacto ambiental
              positivo.
            </p>
          </div>

          <div>
            <h4 className="font-semibold mb-3 text-sm uppercase tracking-wide text-secondary">Plataforma</h4>
            <ul className="space-y-2 text-sm">
              <li><Link to="/" className="hover:text-secondary">Home</Link></li>
              <li><Link to="/sobre" className="hover:text-secondary">Sobre</Link></li>
              <li><Link to="/solucao" className="hover:text-secondary">Solução</Link></li>
              <li><Link to="/experiencia" className="hover:text-secondary">Experiência</Link></li>
            </ul>
          </div>

          <div>
            <h4 className="font-semibold mb-3 text-sm uppercase tracking-wide text-secondary">Suporte</h4>
            <ul className="space-y-2 text-sm">
              <li><Link to="/faq" className="hover:text-secondary">FAQ</Link></li>
              <li><Link to="/contato" className="hover:text-secondary">Contato</Link></li>
              <li><Link to="/integrantes" className="hover:text-secondary">Equipe</Link></li>
            </ul>
          </div>

          <div>
            <h4 className="font-semibold mb-3 text-sm uppercase tracking-wide text-secondary">Parceria</h4>
            <ul className="space-y-2 text-sm">
              <li>
                <a href="https://www.meuprospera.com.br/soul-up/" target="_blank" rel="noopener noreferrer" className="hover:text-secondary">
                  Prospera
                </a>
              </li>
              <li>
                <a href="https://www.fiap.com.br" target="_blank" rel="noopener noreferrer" className="hover:text-secondary">
                  FIAP
                </a>
              </li>
            </ul>
          </div>
        </div>

        <div className="mt-10 pt-6 border-t border-white/10 flex flex-col sm:flex-row items-center justify-between gap-3 text-xs text-secondary">
          <div>© {year} SoulUp · Challenge FIAP 2026 · Projeto acadêmico · Equipe Ecoeye</div>
          <span className="rounded-full bg-white/10 px-3 py-1">1TDSPK Fevereiro</span>
        </div>
      </div>
    </footer>
  )
}
