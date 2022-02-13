import * as api from './api';
import { dict, compute } from '@fast-crud/fast-crud';
import { useMessage } from 'naive-ui';
export default function ({ expose,resourceDrawerRef }) {
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
          edit: { show: false },
          remove: { show: false },
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
        actionName: {
          title: '操作名称',
          key: 'actionName',
          type: 'text',
          search: { show: true },
          column: {
            width: 120,
          },
          form: {
            show: true,
          },
        },
        userName: {
          title: '用户',
          key: 'userName',
          type: 'text',
          column: {
            width: 100,
          },
          form: {
            show: false,
          },
        },
        clientType: {
          title: '端类型',
          key: 'clientType',
          column: {
            width: 120,
          },
          form: {
            show: false,
          },
        },
        platformType: {
          title: '平台',
          key: 'platformType',
          column: {
            width: 120,
          },
          form: {
            show: false,
          },
        },
        requestIp: {
          title: '请求IP',
          key: 'requestIp',
          column: {
            width: 120,
          },
          form: {
            show: false,
          },
        },
        requestAt: {
          title: '操作时间',
          key: 'requestAt',
          column: {
            width: 120,
          },
          form: {
            show: false,
          },
        },
        useTime: {
          title: '耗时',
          key: 'useTime',
          column: {
            width: 80,
          },
          form: {
            show: false,
          },
        },
        responseCode: {
          title: '结果',
          column:{
              width: 100,
              cellRender(scope){
                if(scope.value === 200)
                  return <n-tag type="success"> 成功 </n-tag>;
                else 
                  return <n-tag type="warning"> 失败 </n-tag>;
              }
          },
          form: {
            show: false,
          },
        },
      },
    },
  };
}
