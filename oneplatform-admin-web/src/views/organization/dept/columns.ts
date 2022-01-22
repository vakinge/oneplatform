import { h } from 'vue';
import { NTag } from 'naive-ui';

export const columns = [
  {
    title: '部门名称',
    key: 'name',
  },
  {
    title: '是否启用',
    key: 'enabled',
    render(row) {
      return h(
        NTag,
        {
          type: row.enabled ? 'success' : 'error',
        },
        {
          default: () => (row.enabled ? '是' : '否'),
        }
      );
    },
  },
  {
    title: '排序',
    key: 'sort',
  },
  {
    title: '创建时间',
    key: 'createdAt',
  },
];
