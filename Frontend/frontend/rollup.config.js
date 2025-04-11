import { rollup } from 'rollup';
import { terser } from 'rollup-plugin-terser';

export default {
  input: 'src/index.js',
  output: {
    file: 'dist/index.js',
    format: 'esm',
  },
  plugins: [
    terser(),
    {
      name: 'dynamic-import',
      resolveId(id) {
        if (id.startsWith('react-yandex-maps')) {
          return `import(${id})`;
        }
        return null;
      },
    },
  ],
};