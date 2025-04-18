import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Card, Descriptions, Tabs, Table, Typography, Spin, message } from 'antd';
import { getInterfaceInfoVoByIdUsingGet } from '@/services/shuoapi-backend/interfaceInfoController';

const { Title, Paragraph } = Typography;

const InterfaceInfoDetail: React.FC = () => {
  const { id } = useParams();
  const [info, setInfo] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (id) {
      getInterfaceInfoVoByIdUsingGet({ id })
        .then(res => {
          setInfo(res?.data);
        })
        .catch(() => {
          message.error('获取接口详情失败');
        })
        .finally(() => setLoading(false));
    }
  }, [id]);

  if (loading) return <Spin style={{ marginTop: 100 }} />;

  if (!info) return <div style={{ padding: 24 }}>未找到接口信息</div>;

  // 示例参数和返回值结构
  const requestParams = info.requestParams || [
    { name: 'text', type: 'string', required: '是', desc: '内容' },
  ];
  const responseParams = info.responseParams || [
    { name: 'code', type: 'int', desc: '状态码' },
    { name: 'data', type: 'object', desc: '返回数据' },
    { name: 'message', type: 'string', desc: '提示信息' },
  ];

  return (
    <div style={{ background: '#fafafa', minHeight: '100vh', padding: 24 }}>
      <Card style={{ marginBottom: 24 }}>
        <Descriptions title={info.name} column={2}>
          <Descriptions.Item label="接口地址">{info.url}</Descriptions.Item>
          <Descriptions.Item label="返回格式">{info.responseType || 'JSON'}</Descriptions.Item>
          <Descriptions.Item label="调用次数">{info.callNo}</Descriptions.Item>
          <Descriptions.Item label="请求方式">{info.method}</Descriptions.Item>
          <Descriptions.Item label="接口状态">{info.status === 1 ? '已上线' : '未上线'}</Descriptions.Item>
        </Descriptions>
        <Paragraph style={{ marginTop: 16, color: '#888' }}>{info.description}</Paragraph>
      </Card>

      <Card>
        <Tabs defaultActiveKey="api">
          <Tabs.TabPane tab="API文档" key="api">
            <Title level={5} style={{ marginTop: 0 }}>请求参数说明：</Title>
            <Table
              size="small"
              pagination={false}
              dataSource={requestParams}
              rowKey="name"
              columns={[
                { title: '参数名称', dataIndex: 'name', key: 'name' },
                { title: '参数类型', dataIndex: 'type', key: 'type' },
                { title: '是否必填', dataIndex: 'required', key: 'required' },
                { title: '描述', dataIndex: 'desc', key: 'desc' },
              ]}
            />
            <Title level={5} style={{ marginTop: 24 }}>返回参数说明：</Title>
            <Table
              size="small"
              pagination={false}
              dataSource={responseParams}
              rowKey="name"
              columns={[
                { title: '参数名称', dataIndex: 'name', key: 'name' },
                { title: '类型', dataIndex: 'type', key: 'type' },
                { title: '描述', dataIndex: 'desc', key: 'desc' },
              ]}
            />
            <Title level={5} style={{ marginTop: 24 }}>返回示例：</Title>
            <pre style={{ background: '#f6f8fa', padding: 16, borderRadius: 6 }}>
{JSON.stringify(info.responseExample || {
  code: 0,
  data: { value: '示例返回内容' },
  message: '返回的提示信息'
}, null, 2)}
            </pre>
          </Tabs.TabPane>
          <Tabs.TabPane tab="在线调试工具" key="test">
            <div>这里可以放置在线调试表单或工具</div>
          </Tabs.TabPane>
          <Tabs.TabPane tab="返回示例" key="example">
            <pre style={{ background: '#f6f8fa', padding: 16, borderRadius: 6 }}>
{JSON.stringify(info.responseExample || {
  code: 0,
  data: { value: '示例返回内容' },
  message: '返回的提示信息'
}, null, 2)}
            </pre>
          </Tabs.TabPane>
        </Tabs>
      </Card>
    </div>
  );
};

export default InterfaceInfoDetail;