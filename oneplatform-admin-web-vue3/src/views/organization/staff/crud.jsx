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
        //固定右侧
        fixed: 'right',
      },
      table: {
        //当你表格宽度大到需要使用固定列时，需要设置此值，并且是大于等于列宽度之和的值
        scrollX: 1500,
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
          title: '姓名',
          key: 'name',
          type: 'text',
          search: { show: true },
          column: {
            width: 70,
            fixed: 'left',
          },
          form: {
            show: true,
          },
        },
        code: {
          title: '员工号',
          key: 'code',
          type: 'text',
          column: {
            width: 80,
          },
          form: {
            show: false,
          },
        },
        birthday: {
          title: '生日',
          key: 'birthday',
          column: {
            width: 80,
          },
          form: {
            show: false,
          },
        },
        isLeader: {
          title: '是否负责人',
          column:{
              width: 90,
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
        entryDate: {
          title: '入职时间',
          key: 'entryDate',
          column: {
            width: 80,
          },
        },
        enabled: {
          title: '在职状态',
          column:{
              width: 70,
              cellRender(scope){
                if(scope.value)
                  return <n-tag type="success"> 在职 </n-tag>;
                else 
                  return <n-tag type="warning"> 已离职 </n-tag>;
              }
          },
          form: {
            show: false,
          },
        },
        updatedAt: {
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
