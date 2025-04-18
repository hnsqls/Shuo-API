import { BookOutlined } from '@ant-design/icons';
import '@umijs/max';
export type SiderTheme = 'light' | 'dark';
export const Question = () => {
  return (
    <div
      style={{
        display: 'flex',
        alignItems: 'center',
        height: 26,
        cursor: 'pointer',
        color: '#666',
        fontSize: 16,
        gap: 6,
      }}
      onClick={() => {
        window.open('https://github.com/hnsqls/Shuo-API');
      }}
    >
      <BookOutlined style={{ fontSize: 16, color: '#8ecae6' }} />
      <span>开发者文档</span>
    </div>
  );
};
