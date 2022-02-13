import * as api from './api';
import { dict, compute } from '@fast-crud/fast-crud';
import { useMessage,useDialog } from 'naive-ui';

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
  const dialog = useDialog();
  return {
    crudOptions: {
      request: {
        pageRequest,
        addRequest,
        editRequest,
        delRequest,
      },
      form: {
        display: 'flex',
      },
      actionbar: {
        show: true,
        buttons: {
          add: {
            show: true,
          },
        },
      },
      rowHandle: {
        //固定右侧
        //fixed: 'right',
        width: '150px',
        buttons: {
          view: { show: false },
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
          title: '账号',
          key: 'name',
          type: 'text',
          search: { show: true },
          column: {
            width: 80,
            fixed: 'left',
          },
          form: {
            show: true,
          },
        },
        avatar: {
          title: "头像",
          type: "cropper-uploader",
          column: {
            width: 60,
          },
          form: {
            col: { span: 24 },
          }
        },
        email: {
          title: '邮箱',
          key: 'email',
          type: 'text',
          column: {
            width: 100,
          },
          form: {
            show: true,
          },
        },
        mobile: {
          title: '手机',
          key: 'mobile',
          column: {
            width: 80,
          },
          form: {
            show: true,
          },
        },
        staffName: {
          title: '关联员工',
          key: 'staffName',
          column: {
            width: 80,
          },
          form: {
            show: true,
          },
        },
        enabled: {
          title: '状态',
          search: { show: true },
          type: 'dict-switch',
          form: {show: false},
          column: {
            width: 80,
            component: {
              name: 'fs-dict-switch',
              vModel: 'value',
              'onUpdate:value': compute((context) => {
                return async () => {
                  let btntxt = context.row.enabled ? '启用' : '禁用';
                  await dialog.info({
                    title: '确认',
                    content: `确定${btntxt}该账号么？`,
                    positiveText: '确定',
                    negativeText: '取消',
                    async onPositiveClick() {
                      await api.toggleObj(context.row.id);
                      message.info('操作成功');
                    },
                  });
                };
              }),
            },
          },
          dict: dict({
            data: [
              { value: true, label: '开启' },
              { value: false, label: '关闭' },
            ],
          }),
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
