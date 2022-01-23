import * as api from './api';
import { dict } from '@fast-crud/fast-crud';
import { ref } from 'vue';
export default function ({ expose }) {
  const pageRequest = async (query) => {
    return await api.GetList(query);
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
  return {
    crudOptions: {
      request: {
        pageRequest,
        addRequest,
        editRequest,
        delRequest,
      },
      table: {
        // 表头过滤改变事件
        onFilterChange(e) {
          console.log('onFilterChange', e);
        },
      },
      columns: {
        id: {
          title: 'ID',
          key: 'id',
          type: 'number',
          column: {
            width: 50,
          },
          form: {
            show: false,
          },
        },
        valueBuilder: {
          title: 'valueBuilder',
          type: 'number',
          search: {
            show: true,
            valueResolve({ key, value, form }) {
              if (value) {
                //可以转化查询条件
                form[key] = value + '';
              }
            },
          },
        },
      },
    },
  };
}
