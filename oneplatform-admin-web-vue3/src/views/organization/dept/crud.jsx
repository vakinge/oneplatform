import * as api from './api';
import { dict, compute } from '@fast-crud/fast-crud';
import { useMessage } from 'naive-ui';
export default function ({ expose }) {
  const pageRequest = async (query) => {
    let data = await api.GetList(query);
    return {
      pageNo: 1,
      pageSize: data.length,
      data: data,
    };
  };
  const editRequest = async ({ form, row }) => {
    form.id = row.id;
    return await api.UpdateObj(form);
  };
  const delRequest = async ({ row }) => {
    return await api.DelObj(row.id);
  };

  const addRequest = async ({ form }) => {
    return await api.AddObj(form);
  };
  const message = useMessage();
  return {
    crudOptions: {
      request: {
        pageRequest,
        addRequest,
        editRequest,
        delRequest,
      },
      columns: {
        name: {
          title: '名称',
          key: 'name',
          type: 'text',
          search: { show: true },
          column: {
            width: 120,
          },
          form: {
            show: true,
          },
        },
        code: {
          title: '编码',
          key: 'code',
          type: 'text',
          column: {
            width: 120,
          },
          form: {
            show: true,
          },
        },
        createdAt: {
          title: '创建时间',
          key: 'createdAt',
          type: 'text',
          column: {
            width: 120,
          },
          form: {
            show: false,
          },
        },
      },
      pagination:{show: false},
    },
  };
}
