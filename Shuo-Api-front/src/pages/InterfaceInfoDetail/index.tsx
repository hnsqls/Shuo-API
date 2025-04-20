import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Card, Descriptions, Tabs, Table, Typography, Spin, message, Button } from 'antd';
import { getInterfaceInfoVoByIdUsingGet } from '@/services/shuoapi-backend/interfaceInfoController';
import { Form, Input, Select, Space, message as antdMessage } from 'antd';
import { PlusOutlined, DeleteOutlined } from '@ant-design/icons';

const { Title, Paragraph } = Typography;

const InterfaceInfoDetail: React.FC = () => {
  const { id } = useParams();
  const [info, setInfo] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const [paramsTable, setParamsTable] = useState<{ key: string; name: string; value: string }[]>([]);
  const [method, setMethod] = useState('GET');
  const [url, setUrl] = useState('');
  const [testLoading, setTestLoading] = useState(false);
  const [testResult, setTestResult] = useState<any>(null);

  useEffect(() => {
    if (id) {
      getInterfaceInfoVoByIdUsingGet({ id })
        .then(res => {
          setInfo(res?.data);
          // 兼容 requestParams 为字符串或对象
          let reqParams: any = res?.data?.requestParams || [];
          if (typeof reqParams === 'string') {
            try {
              reqParams = JSON.parse(reqParams);
            } catch {
              reqParams = [];
            }
          }
          // 如果是对象（如 {username: "admin", password: "123456"}），转成数组
          if (!Array.isArray(reqParams) && typeof reqParams === 'object' && reqParams !== null) {
            reqParams = Object.entries(reqParams).map(([key, value], idx) => ({
              key: String(idx),
              name: key,
              value: value,
            }));
          } else if (Array.isArray(reqParams)) {
            reqParams = reqParams.map((p: any, idx: number) => ({
              key: String(idx),
              name: p.name,
              value: '',
            }));
          } else {
            reqParams = [];
          }
          setParamsTable(reqParams);
          setMethod(res?.data?.method || 'GET'); // 动态设置method
          setUrl(res?.data?.url || '');          // 动态设置url
        })
        .catch(() => {
          message.error('获取接口详情失败');
        })
        .finally(() => setLoading(false));
    }
  }, [id]);

  // 添加一行参数
  const addParamRow = () => {
    setParamsTable([...paramsTable, { key: Date.now().toString(), name: '', value: '' }]);
  };

  // 删除一行参数
  const removeParamRow = (key: string) => {
    setParamsTable(paramsTable.filter(row => row.key !== key));
  };

  // 修改参数
  const updateParam = (key: string, field: 'name' | 'value', val: string) => {
    setParamsTable(paramsTable.map(row => row.key === key ? { ...row, [field]: val } : row));
  };

  // 重置参数
  const resetParams = () => {
    setParamsTable(
      (info.requestParams || []).map((p: any, idx: number) => ({
        key: String(idx),
        name: p.name,
        value: '',
      }))
    );
    setTestResult(null);
  };

  if (loading) return <Spin style={{ marginTop: 100 }} />;

  if (!info) return <div style={{ padding: 24 }}>未找到接口信息</div>;

  // 示例参数和返回值结构
  let requestParams: any[] = [];
  let responseParams: any[] = [];
  let responseExample: any = {};
  try {
    let parsed = info.requestParams
      ? (typeof info.requestParams === 'string'
          ? JSON.parse(info.requestParams)
          : info.requestParams)
      : [
          { name: 'text', type: 'string', required: '是', desc: '内容' },
        ];
    // 如果是对象（不是数组），转成数组
    requestParams = Array.isArray(parsed)
      ? parsed
      : Object.entries(parsed).map(([key, value]) => ({
          name: key,
          value: value,
          type: typeof value,
          required: '',
          desc: ''
        }));
  } catch {
    requestParams = [];
  }
  try {
    let parsed = info.responseParams
      ? (typeof info.responseParams === 'string'
          ? JSON.parse(info.responseParams)
          : info.responseParams)
      : {
          code: 0,
          data: { value: '示例返回内容' },
          message: '返回的提示信息'
        };
    // 如果是对象（不是数组），转成数组
    responseParams = Array.isArray(parsed)
      ? parsed
      : Object.entries(parsed).map(([key, value]) => ({
          name: key,
          value: value,
          type: typeof value,
          desc: ''
        }));
    responseExample = parsed;
  } catch {
    responseParams = [];
    responseExample = {};
  }

  // 在线调试提交
  const handleTest = async () => {
    if (!id) return;
    setTestLoading(true);
    setTestResult(null);
    try {
      // 构造参数对象
      const paramsObj: Record<string, any> = {};
      paramsTable.forEach((row) => {
        if (row.name) paramsObj[row.name] = row.value;
      });

      let res;
      // 根据 method 动态选择请求方式
      if (method === 'GET') {
        // 这里请替换为你的 GET 请求方法
        res = await getInterfaceInfoVoByIdUsingGet({
          id,
          ...paramsObj,
        });
      } else if (method === 'POST') {
        // 这里请替换为你的 POST 请求方法
        // res = await postInterfaceInfo({ id, ...paramsObj });
        res = { code: 0, data: null, message: 'POST请求示例（请替换为实际API）' };
      } else {
        // 其他请求类型可按需扩展
        res = { code: 0, data: null, message: `暂不支持${method}请求，请补充实现` };
      }

      setTestResult(res);
      antdMessage.success('调试成功');
    } catch (e) {
      setTestResult(e?.message || '调试失败');
      antdMessage.error('调试失败');
    }
    setTestLoading(false);
  };

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
            {/* 新增请求示例 */}
            <Title level={5} style={{ marginTop: 24 }}>请求示例：</Title>
            <pre style={{ background: '#f6f8fa', padding: 16, borderRadius: 6 }}>
        {JSON.stringify(
          typeof info.requestParams === 'string'
            ? JSON.parse(info.requestParams)
            : info.requestParams,
          null,
          2
        )}
          </pre>
          <Title level={5} style={{ marginTop: 24 }}>返回示例：</Title>
          <pre style={{ background: '#f6f8fa', padding: 16, borderRadius: 6 }}>
        {JSON.stringify(responseExample, null, 2)}
          </pre>
          </Tabs.TabPane>
          <Tabs.TabPane tab="在线调试工具" key="test">
            <div style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 16 }}>
              <Select value={method} style={{ width: 90 }} disabled>
                <Select.Option value="GET">GET</Select.Option>
                <Select.Option value="POST">POST</Select.Option>
                <Select.Option value="PUT">PUT</Select.Option>
                <Select.Option value="DELETE">DELETE</Select.Option>
              </Select>
              <Input
                value={url}
                style={{ flex: 1 }}
                onChange={e => setUrl(e.target.value)}
              />
              <Button type="primary" loading={testLoading} onClick={handleTest}>
                发起请求
              </Button>
            </div>
            <div style={{ fontWeight: 500, margin: '16px 0 8px 0' }}>请求参数设置：</div>
            <Table
              dataSource={paramsTable}
              pagination={false}
              rowKey="key"
              bordered
              size="small"
              style={{ marginBottom: 8 }}
              columns={[
                {
                  title: '参数名称',
                  dataIndex: 'name',
                  render: (text, record) => (
                    <Input
                      value={text}
                      placeholder="参数名"
                      onChange={e => updateParam(record.key, 'name', e.target.value)}
                    />
                  ),
                },
                {
                  title: '参数值',
                  dataIndex: 'value',
                  render: (text, record) => (
                    <Input
                      value={text}
                      placeholder="参数值"
                      onChange={e => updateParam(record.key, 'value', e.target.value)}
                    />
                  ),
                },
                {
                  title: '操作',
                  dataIndex: 'action',
                  width: 60,
                  render: (_, record) => (
                    <Button
                      icon={<DeleteOutlined />}
                      danger
                      size="small"
                      onClick={() => removeParamRow(record.key)}
                    />
                  ),
                },
              ]}
              locale={{
                emptyText: (
                  <div style={{ color: '#bbb', padding: 16 }}>
                    暂无数据
                  </div>
                ),
              }}
            />
            <div style={{ marginBottom: 16 }}>
              <Button
                type="dashed"
                block
                icon={<PlusOutlined />}
                onClick={addParamRow}
              >
                添加一行数据
              </Button>
            </div>
            <Button onClick={resetParams} style={{ marginBottom: 16 }} type="primary" ghost>
              重置
            </Button>
            <div style={{ fontWeight: 500, margin: '16px 0 8px 0' }}>返回结果：</div>
            <pre style={{
              background: '#f6f8fa',
              padding: 16,
              borderRadius: 6,
              minHeight: 80,
              fontFamily: 'monospace',
              fontSize: 14,
            }}>
              {testResult
                ? (typeof testResult === 'object'
                  ? JSON.stringify(testResult, null, 2)
                  : String(testResult))
                : ''}
            </pre>
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