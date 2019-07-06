import React from 'react';

import zhCN from 'antd/lib/locale-provider/zh_CN';
import { LocaleProvider } from 'antd';
import GlobalRouter from './router/GlobalRouter';

function App() {
  return (
    <div>
      <LocaleProvider locale={zhCN}>
          <GlobalRouter/>
        </LocaleProvider>
    </div>
  );
}

export default App;
