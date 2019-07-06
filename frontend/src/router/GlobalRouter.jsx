import React from 'react';

import { BrowserRouter, Route} from 'react-router-dom';
import LoginPage from '../page/login';
import MainPage from '../page/main';
import Demo from '../Demo'

class GlobalRouter extends React.Component {
  render() {
      return (
        <BrowserRouter>
            <Route exact path="/" component={LoginPage}/>
            <Route path="/login" component={LoginPage}/>
            <Route path="/main" component={MainPage}/>
            <Route path="/demo" component={Demo}/>
        </BrowserRouter>);
  }
}

export default GlobalRouter;