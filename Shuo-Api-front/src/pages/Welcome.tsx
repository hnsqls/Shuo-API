import React from 'react';
import { Button } from 'antd';
import { useNavigate } from 'react-router-dom'; // 新增

const features = [
  {
    color: '#3b82f6',
    title: '多样化的接口选择',
    desc: '平台提供丰富多样的接口选择，涵盖多个领域的功能和服务，满足不同需求。',
    icon: '🟦',
  },
  {
    color: '#10b981',
    title: '在线调试功能',
    desc: '支持接口的在线调试，快速验证接口的功能和效果。',
    icon: '🟩',
  },
  {
    color: '#a855f7',
    title: '客户端SDK支持',
    desc: '平台为开发者提供易用的SDK，支持多端集成，提升开发效率。',
    icon: '🟪',
  },
  {
    color: '#f59e42',
    title: '开发文档和技术支持',
    desc: '提供详细的接口文档和技术支持，帮助开发者快速接入和使用。',
    icon: '🟧',
  },
  {
    color: '#06b6d4',
    title: '稳定和安全',
    desc: '采用先进架构，保障API服务的稳定和安全，数据传输加密，保障用户数据安全。',
    icon: '🟦',
  },
];

const Welcome: React.FC = () => {
  const navigate = useNavigate(); // 新增

  return (
    <div
      style={{
        minHeight: '100vh',
        width: '100vw',
        background: 'linear-gradient(90deg, #fff 60%, #f0f9ff 100%)',
        position: 'absolute',
        left: 0,
        top: 0,
        zIndex: 0,
      }}
    >
      <div
        style={{
          minHeight: '100vh',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          maxWidth: 1200,
          margin: '0 auto',
          position: 'relative',
          zIndex: 1,
        }}
      >
        {/* 左侧介绍 */}
        <div style={{ flex: 1, maxWidth: 520, marginRight: 48 }}>
          <div style={{ fontSize: 40, fontWeight: 700, marginBottom: 16 }}>
          Shuo-API 接口开放平台 <span role="img" aria-label="party">🎉</span>
          </div>
          <div style={{ fontSize: 18, color: '#555', marginBottom: 16 }}>
          Shuo-API 接口开放平台是一个为用户和开发者提供全面API接口调用服务的平台。
          </div>
          <div style={{ fontSize: 16, color: '#888', marginBottom: 24 }}>
            —— 极速响应，让速度为您见证一切！
          </div>
          <div style={{ display: 'flex', gap: 16 }}>
            <Button
              type="primary"
              size="large"
              onClick={() => navigate('/interfacelist')} // 新增跳转
            >
              开始使用
            </Button>
            <Button size="large">查看文档</Button>
          </div>
        </div>
        {/* 右侧优势卡片 */}
        <div style={{
          flex: 1.2,
          background: '#fff',
          borderRadius: 18,
          boxShadow: '0 4px 24px rgba(0,0,0,0.06)',
          padding: 36,
          minWidth: 380,
          maxWidth: 480,
        }}>
          <div style={{ fontSize: 22, fontWeight: 600, marginBottom: 18 }}>稳定运营</div>
          <div style={{ color: '#666', fontSize: 15, marginBottom: 24 }}>
            我们为您提供可持续高质量的服务，采用先进的技术架构，确保API服务的稳定性和可用性。
          </div>
          <div>
            {features.map((f, idx) => (
              <div key={f.title} style={{ display: 'flex', alignItems: 'flex-start', marginBottom: 22 }}>
                <span style={{
                  fontSize: 28,
                  marginRight: 18,
                  color: f.color,
                  width: 36,
                  textAlign: 'center',
                  lineHeight: '36px',
                }}>{f.icon}</span>
                <div>
                  <div style={{ fontWeight: 500, fontSize: 16, marginBottom: 4 }}>{f.title}</div>
                  <div style={{ color: '#888', fontSize: 14 }}>{f.desc}</div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Welcome;