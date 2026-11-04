import type { FilterOption } from '../../types'

interface FilterChipsProps<T extends string> {
  label: string
  options: FilterOption<T>[]
  active: T
  onChange: (value: T) => void
}

export default function FilterChips<T extends string>({ label, options, active, onChange }: FilterChipsProps<T>) {
  return (
    <div role="group" aria-label={label} className="flex flex-wrap gap-2">
      {options.map((option) => {
        const isActive = option.value === active
        return (
          <button
            key={option.value}
            type="button"
            onClick={() => onChange(option.value)}
            aria-pressed={isActive}
            className={`inline-flex items-center gap-2 rounded-full px-4 py-2 text-sm font-medium transition-colors ${
              isActive ? 'bg-primary text-white' : 'bg-white border border-border text-text-dark hover:border-primary-light'
            }`}
          >
            {option.label}
            {option.count !== undefined && (
              <span className={`text-xs rounded-full px-1.5 ${isActive ? 'bg-white/20' : 'bg-bg-alt'}`}>{option.count}</span>
            )}
          </button>
        )
      })}
    </div>
  )
}
