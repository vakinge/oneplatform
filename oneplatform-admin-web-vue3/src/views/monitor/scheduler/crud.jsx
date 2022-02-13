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
      rowHandle: {
        show: true,
        width: 100,
        buttons: {
          view: { show: false },
          edit: { show: false },
          remove: { show: false },
          custom: {
            text: '执行',
            order: 0,
            size: 'small',
            show: true,
            click(context) {
              //context.row.id
            },
          },
        },
      },
      columns: {
        jobName: {
          title: '任务名称',
          key: 'jobName',
          type: 'text',
          column: {
            width: 100,
          },
          form: {
            show: true,
          },
        },
        groupName: {
          title: '分组',
          key: 'groupName',
          type: 'text',
          search: { show: true },
          column: {
            width: 100,
          },
          form: {
            show: false,
          },
        },
        cronExpr: {
          title: '时间表达式',
          key: 'cronExpr',
          column: {
            width: 120,
          },
          form: {
            show: false,
          },
        },
        currentNodeId: {
          title: '当前节点',
          key: 'currentNodeId',
          column: {
            width: 120,
          },
          form: {
            show: false,
          },
        },
        lastFireTime: {
          title: '上次执行时间',
          key: 'lastFireTime',
          column: {
            width: 120,
          },
          form: {
            show: false,
          },
        },
        nextFireTime: {
          title: '下次执行时间',
          key: 'nextFireTime',
          column: {
            width: 120,
          },
          form: {
            show: false,
          },
        },
        running: {
          title: '运行状态',
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
        active: {
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
      },
      pagination:{show: false},
    },
  };
}
