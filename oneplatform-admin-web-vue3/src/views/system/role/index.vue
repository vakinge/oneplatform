<template>
  <fs-page>
    <fs-crud ref="crudRef" v-bind="crudBinding">
      <template #pagination-left>
        <fs-button icon="ion:trash-outline" @click="handleBatchDelete" />
      </template>
    </fs-crud>
  </fs-page>
  <ResourceDrawer ref="resourceDrawerRef" title="编辑权限" />
</template>

<script>
  import { defineComponent, ref, onMounted } from 'vue';
  import { useCrud,useExpose } from '@fast-crud/fast-crud';
  import createCrudOptions from './crud';
  import { useDialog, useMessage } from 'naive-ui';
  import ResourceDrawer from './ResourceDrawer.vue';
  export default defineComponent({
    name: 'FunctionRole',
    setup() {
      // crud组件的ref
      const crudRef = ref();
      // crud 配置的ref
      const crudBinding = ref();
      // 暴露的方法
      const { expose } = useExpose({ crudRef, crudBinding });
      // 你的crud配置
      const { crudOptions } = createCrudOptions({ expose });
      // 初始化crud配置
      // eslint-disable-next-line @typescript-eslint/no-unused-vars,no-unused-vars
      const { resetCrudOptions } = useCrud({ expose, crudOptions });
      
      const resourceDrawerRef = ref();
      // 你可以调用此方法，重新初始化crud配置
      // resetCrudOptions(options)

      // 页面打开后获取列表数据
      onMounted(() => {
        expose.doRefresh();
      });
      
      const message = useMessage();
      const dialog = useDialog();
      const handleBatchDelete = async () => {
        if (selectedIds.value?.length > 0) {
          await dialog.info({
            title: '确认',
            content: `确定要批量删除这${selectedIds.value.length}条记录吗`,
            positiveText: '确定',
            negativeText: '取消',
            async onPositiveClick() {
              await BatchDelete(selectedIds.value);
              message.info('删除成功');
              selectedIds.value = [];
              await expose.doRefresh();
            },
          });
        } else {
          message.error('请先勾选记录');
        }
      };
      
      const openResourceDrawer = () => {
        const { openDrawer } = resourceDrawerRef.value;
        openDrawer();
      }

      return {
        crudBinding,
        crudRef,
        handleBatchDelete,
        openResourceDrawer,
      };
    },
  });

</script>
