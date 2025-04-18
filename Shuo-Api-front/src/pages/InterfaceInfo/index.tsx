import React, { useEffect, useState } from 'react';
import { Input, Badge, Card, Row, Col, Button } from 'antd';
import { AppstoreOutlined } from '@ant-design/icons';
import { listInterfaceInfoByPageUsingPost } from '@/services/shuoapi-backend/interfaceInfoController';
import { useNavigate } from 'react-router-dom';

const { Search } = Input;

const InterfaceInfoList: React.FC = () => {
  const [data, setData] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [searchValue, setSearchValue] = useState('');
  const navigate = useNavigate();

  // 获取接口列表
  const fetchData = async (keyword = '') => {
    setLoading(true);
    const res = await listInterfaceInfoByPageUsingPost({
      name: keyword,
      pageSize: 20,
      current: 1,
    });
    setData(res?.data?.records || []);
    setLoading(false);
  };

  useEffect(() => {
    fetchData();
  }, []);

  // 搜索
  const onSearch = (value: string) => {
    setSearchValue(value);
    fetchData(value);
  };

  return (
    <div style={{ background: '#fafafa', minHeight: '100vh', padding: 24 }}>
      {/* 搜索框 */}
      <div style={{ background: '#fff', padding: 24, borderRadius: 8, marginBottom: 24 }}>
        <Search
          placeholder="没有找到心仪的接口？快搜索一下吧"
          enterButton="搜索"
          size="large"
          maxLength={50}
          value={searchValue}
          onChange={e => setSearchValue(e.target.value)}
          onSearch={onSearch}
        />
      </div>

      {/* 卡片列表 */}
      <Row gutter={[24, 24]}>
        {data.map(item => (
          <Col xs={24} sm={12} md={8} lg={6} xl={4} key={item.id}>
            <Card
              hoverable
              style={{ borderRadius: 12, minHeight: 280, height: 280, position: 'relative', width: '100%', cursor: 'pointer' }}
              bodyStyle={{ paddingTop: 12, minHeight: 180 }}
              onClick={() => navigate(`/interfaceinfo/${item.id}`)} // 点击跳转
              cover={
                <div style={{ textAlign: 'center', padding: '32px 0 0 0' }}>
                  <img
                    alt={item.name}
                    src={item.avatar || 'https://pic.ntimg.cn/file/20201028/30449263_212331007082_2.jpg'}
                    style={{ width: 80, height: 80, objectFit: 'cover', borderRadius: 8 }}
                  />
                </div>
              }
            >
              <div style={{ position: 'absolute', top: 16, right: 16 }}>
                <Badge count={item.callNo || 0} style={{ backgroundColor: '#f5222d' }} />
              </div>
              <div style={{ fontWeight: 600, fontSize: 18, marginBottom: 8 }}>{item.name}</div>
              <div style={{ color: '#888', minHeight: 40 }}>{item.description}</div>
            </Card>
          </Col>
        ))}
      </Row>

      {/* 右下角布局切换按钮 */}
      <div style={{ position: 'fixed', right: 40, bottom: 40 }}>
        <Button shape="circle" size="large" icon={<AppstoreOutlined />} />
      </div>
    </div>
  );
};

export default InterfaceInfoList;