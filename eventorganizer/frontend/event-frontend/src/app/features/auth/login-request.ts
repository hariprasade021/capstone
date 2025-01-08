import { Role } from './../../core/model/User';
export interface LoginRequest {
  username: string;
  password: string;
  role:Role
}
