import { Link } from 'react-router-dom'
import StatusBadge from '../common/StatusBadge'
import UsuarioAvatar from './UsuarioAvatar'
import { ArrowRightIcon } from '../icons/Icons'
import { getSituacaoUsuarioInfo } from '../../data/situacoes'
import { formatApiDate } from '../../utils/format'
import type { Usuario } from '../../types/api'

interface UsuarioListProps {
  usuarios: Usuario[]
}

const actionClasses =
  'inline-flex items-center rounded-full border border-border px-3 py-1.5 text-xs font-semibold text-primary hover:border-primary-light transition-colors'

/** Lista em cards no celular e em tabela a partir do tablet (md). */
export default function UsuarioList({ usuarios }: UsuarioListProps) {
  return (
    <>
      <ul className="md:hidden space-y-3">
        {usuarios.map((usuario) => {
          const situacao = getSituacaoUsuarioInfo(usuario.situacao)
          return (
            <li key={usuario.id}>
              <Link
                to={`/usuarios/${usuario.id}`}
                className="flex items-center gap-3 rounded-2xl border border-border bg-white p-4 hover:border-primary-light transition-colors"
              >
                <UsuarioAvatar usuario={usuario} />
                <div className="flex-1 min-w-0">
                  <p className="font-semibold text-text-dark truncate">{usuario.nome}</p>
                  <p className="text-xs text-text-muted truncate">{usuario.email}</p>
                  <div className="mt-2">
                    <StatusBadge label={situacao.label} tone={situacao.tone} />
                  </div>
                </div>
                <ArrowRightIcon className="w-4 h-4 text-primary flex-shrink-0" />
              </Link>
            </li>
          )
        })}
      </ul>

      <div className="hidden md:block overflow-hidden rounded-2xl border border-border bg-white">
        <table className="w-full text-sm text-left">
          <caption className="sr-only">Usuários cadastrados na plataforma SoulUp</caption>
          <thead className="bg-bg-alt text-xs uppercase tracking-wide text-text-muted">
            <tr>
              <th scope="col" className="px-5 lg:px-6 py-3 font-semibold">
                Usuário
              </th>
              <th scope="col" className="px-5 lg:px-6 py-3 font-semibold">
                Situação
              </th>
              <th scope="col" className="hidden lg:table-cell px-6 py-3 font-semibold">
                Cadastro
              </th>
              <th scope="col" className="px-5 lg:px-6 py-3 font-semibold text-right">
                Ações
              </th>
            </tr>
          </thead>
          <tbody className="divide-y divide-border">
            {usuarios.map((usuario) => {
              const situacao = getSituacaoUsuarioInfo(usuario.situacao)
              return (
                <tr key={usuario.id} className="hover:bg-bg-alt/60 transition-colors">
                  <td className="px-5 lg:px-6 py-4">
                    <div className="flex items-center gap-3">
                      <UsuarioAvatar usuario={usuario} />
                      <div className="min-w-0">
                        <p className="font-semibold text-text-dark">{usuario.nome}</p>
                        <p className="text-xs text-text-muted break-all">{usuario.email}</p>
                      </div>
                    </div>
                  </td>
                  <td className="px-5 lg:px-6 py-4">
                    <StatusBadge label={situacao.label} tone={situacao.tone} />
                  </td>
                  <td className="hidden lg:table-cell px-6 py-4 text-text-muted whitespace-nowrap">
                    {formatApiDate(usuario.dataCadastro)}
                  </td>
                  <td className="px-5 lg:px-6 py-4">
                    <div className="flex justify-end gap-2">
                      <Link to={`/usuarios/${usuario.id}`} className={actionClasses} aria-label={`Ver perfil de ${usuario.nome}`}>
                        Ver perfil
                      </Link>
                      <Link to={`/usuarios/${usuario.id}/editar`} className={actionClasses} aria-label={`Editar ${usuario.nome}`}>
                        Editar
                      </Link>
                    </div>
                  </td>
                </tr>
              )
            })}
          </tbody>
        </table>
      </div>
    </>
  )
}
