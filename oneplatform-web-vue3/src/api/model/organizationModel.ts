import { BasicPageParams, BasicFetchResult } from '/@/api/model/baseModel';

export type StaffParams = BasicPageParams & {
  account?: string;
  nickname?: string;
};

export type DepartmentParams = {
  deptName?: string;
  status?: string;
};

export type PostionParams = BasicPageParams & {
  account?: string;
  nickname?: string;
};

export interface Staff {
  id: string;
  account: string;
  email: string;
  nickname: string;
  role: number;
  createTime: string;
  remark: string;
  status: number;
}

export interface Department {
  id: string;
  parentId: string;
  name: string;
  orgType: string;
  name: string;
  sort: number;
  isVirtual: string;
  enabled: boolean;
  createdAt: string;
}

export interface Postion {
  id: string;
  orderNo: string;
  createTime: string;
  remark: string;
  status: number;
}

/**
 * @description: Request list return value
 */
export type StaffListModel = BasicFetchResult<Staff>;

export type DepartmentListModel = BasicFetchResult<Department>;

export type PostionListModel = BasicFetchResult<Postion>;
