<template>
  <n-grid class="mt-4" cols="1 s:1 m:1 l:4 xl:4 2xl:4" responsive="screen" :x-gap="12">
    <n-gi span="1">
      <n-card :segmented="{ content: 'hard' }" :bordered="false" size="small">
      <div class="py-3 menu-list">
        <template v-if="loading">
          <div class="flex items-center justify-center py-4">
            <n-spin size="medium" />
          </div>
        </template>
        <template v-else>
          <n-tree
            block-line
            cascade
            :virtual-scroll="true"
            :data="treeData"
            key-field="id"
            label-field="name"
            style="height: 500px; max-height: 500px; overflow: hidden"
            :default-expanded-keys="defaultExpandedKeys"
          />
        </template>
      </div>
      </n-card>
    </n-gi>
   <n-gi span="3">
     <fs-page>
     <fs-crud ref="crudRef" v-bind="crudBinding">
       <template #pagination-left>
         <fs-button icon="ion:paper-plane-sharp" @click="handleBatchDelete" />
       </template>
     </fs-crud>
     </fs-page>
   </n-gi>
  </n-grid>
</template>

<script>
  import { defineComponent, ref, onMounted } from 'vue';
  import { useCrud } from '@fast-crud/fast-crud';
  import createCrudOptions from './crud';
  import { useExpose } from '@fast-crud/fast-crud';
  import { useDialog, useMessage } from 'naive-ui';
  import { GetList } from '@/views/organization/dept/api';
  export default defineComponent({
    name: 'Staff',
    setup() {
      const loading = ref(true);
      const treeData = ref([]);
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
      // 你可以调用此方法，重新初始化crud配置
      // resetCrudOptions(options)

      // 页面打开后获取列表数据
      onMounted(async () => {
        expose.doRefresh();
        loading.value = false;
        const treeMenuList = await GetList();
        treeData.value = treeMenuList;
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

      return {
        loading,
        treeData,
        crudBinding,
        crudRef,
        handleBatchDelete,
      };
    },
  });
</script>
