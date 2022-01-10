import {
  StaffParams,
  DepartmentParams,
  PostionParams,
  Staff,
  Department,
  Postion,
  StaffListModel,
  DepartmentListModel,
  PostionListModel
} from './model/organizationModel';
import { defHttp } from '/@/utils/http/axios';

enum Api {
  StaffList = '/org/staff/list',
  DeptList = '/org/dept/list'
}

export const getStaffList = (params: StaffParams) =>
  defHttp.get<StaffListGetResultModel>({ url: Api.StaffList, params });

export const getDeptList = (params?: DeptListItem) =>
  defHttp.post<DeptListGetResultModel>({ url: Api.DeptList, params });
