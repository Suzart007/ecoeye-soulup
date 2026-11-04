import { getInitials } from '../../utils/format'
import type { Usuario } from '../../types/api'

const COLORS = ['bg-primary', 'bg-info', 'bg-success', 'bg-primary-light']

interface UsuarioAvatarProps {
  usuario: Pick<Usuario, 'id' | 'nome'>
  size?: 'md' | 'lg'
}

/** Círculo com as iniciais do usuário; a cor varia conforme o id. */
export default function UsuarioAvatar({ usuario, size = 'md' }: UsuarioAvatarProps) {
  const sizeClasses = size === 'lg' ? 'w-20 h-20 md:w-24 md:h-24 text-2xl md:text-3xl' : 'w-11 h-11 text-sm'

  return (
    <span
      aria-hidden="true"
      className={`${sizeClasses} ${COLORS[usuario.id % COLORS.length]} rounded-full text-white font-bold flex items-center justify-center flex-shrink-0`}
    >
      {getInitials(usuario.nome)}
    </span>
  )
}
