// React
import React from 'react';
import {makeObservable, observable, action} from 'mobx';

// Interfaces
import {IUserState} from './interfaces/user.interface';

// Types

export interface IAppState {
  user?: IUserState;
  count: number;
}

class AppState implements IAppState {
  user = {
    isAuthenticated: false,
  };
  count = 0;

  constructor() {
    makeObservable(this, {
      user: observable,
      count: observable,
      increment: action,
      decrement: action,
      toggleAuth: action,
    });
  }

  increment = () => {
    this.count += 1;
  };

  decrement = () => {
    this.count -= 1;
  };

  toggleAuth = () => {
    this.user.isAuthenticated = !this.user.isAuthenticated;
  };
}

const store = new AppState();

export const StoreContext = React.createContext(store);
export const useStore = () => React.useContext(StoreContext);
