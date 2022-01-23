import * as api from './api';
import { dict, compute } from '@fast-crud/fast-crud';
import { useMessage } from 'naive-ui';
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
  const message = useMessage();
  return {
    crudOptions: {
      request: {
        pageRequest,
        addRequest,
        editRequest,
        delRequest,
      },
      rowHandle: {
        show: true,
        width: 330,
        buttons: {
          view: { show: true },
          edit: { show: true },
          remove: { show: true },
          custom: {
            text: '分配权限',
            order: 0,
            size: 'small',
            show: true,
            click(context) {
              parent.openResourceDrawer();
            },
          },
        },
      },
      columns: {
        _checked: {
          title: '选择',
          form: { show: false },
          column: {
            type: 'selection',
            align: 'center',
            width: '55px',
            columnSetDisabled: true, //禁止在列设置中选择
            selectable(row, index) {
              return row.id !== 1; //设置第一行不允许选择
            },
          },
        },
        id: {
          title: 'ID',
          key: 'id',
          type: 'text',
          column: {
            show: false
          },
          form: {
            show: false,
          },
        },
        name: {
          title: '名称',
          key: 'name',
          type: 'text',
          search: { show: true },
          column: {
            width: 100,
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
            width: 100,
          },
          form: {
            show: false,
          },
        },
        birthday: {
          title: '生日',
          key: 'birthday',
          column: {
            width: 120,
          },
          form: {
            show: false,
          },
        },
        isDefault: {
          title: '是否默认',
          column:{
              width: 100,
              cellRender(scope){
                if(scope.value)
                  return <n-tag type="success"> 是 </n-tag>;
                else 
                  return <n-tag type="warning"> 否 </n-tag>;
              }
          },
          form: {
            show: false,
          },
        },
        enabled: {
          title: '启用状态',
          column:{
              width: 100,
              cellRender(scope){
                if(scope.value)
                  return <n-tag type="success"> 启用 </n-tag>;
                else 
                  return <n-tag type="warning"> 禁用 </n-tag>;
              }
          },
          form: {
            show: false,
          },
        },
        birthday: {
          title: '最后更新时间',
          key: 'updatedAt',
          column: {
            width: 120,
          },
          form: {
            show: false,
          },
        },
      },
    },
  };
}
