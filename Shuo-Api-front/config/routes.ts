import { layout } from "@/app";
import { Layout } from "antd";

export default [
  {
    path: '/user',
    layout: false,
    routes: [{ name: '登录', path: '/user/login', component: './User/Login' }],
  },
  { path: '/welcome', name: '欢迎', icon: 'smile', component: './Welcome' },
  {
    path: '/admin',
    name: '管理页',
    icon: 'crown',
    access: 'canAdmin',
    routes: [
      { path: '/admin', redirect: '/admin/sub-page' },
      { path: '/admin/sub-page', name: '接口管理', component: './TableList' },
    ],
  },
  { name: '接口广场', icon: 'table', path: '/interfacelist', component: './InterfaceInfo' },
  { path: '/interfaceinfo/:id',  component: './InterfaceInfoDetail' }, // 新增详情页路由
  { path: '/', redirect: '/welcome' },
  { path: '*', layout: false, component: './404' },
];
