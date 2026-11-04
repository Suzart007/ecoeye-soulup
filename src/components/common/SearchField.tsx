import { SearchIcon } from '../icons/Icons'

interface SearchFieldProps {
  id: string
  label: string
  value: string
  placeholder: string
  onChange: (value: string) => void
}

export default function SearchField({ id, label, value, placeholder, onChange }: SearchFieldProps) {
  return (
    <div>
      <label htmlFor={id} className="sr-only">
        {label}
      </label>
      <div className="flex items-center gap-3 rounded-full border border-border bg-white px-4 py-2.5 focus-within:border-primary transition-colors">
        <SearchIcon className="w-5 h-5 text-text-muted flex-shrink-0" />
        <input
          id={id}
          type="search"
          value={value}
          onChange={(event) => onChange(event.target.value)}
          placeholder={placeholder}
          className="flex-1 min-w-0 outline-none text-sm bg-transparent"
        />
      </div>
    </div>
  )
}
