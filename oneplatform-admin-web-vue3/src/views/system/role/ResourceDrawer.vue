<template>
  <n-drawer v-model:show="isDrawer" :width="width" :placement="placement">
    <n-drawer-content :title="title" closable>     
      <n-tree
        block-line
        cascade
        checkable
        :virtual-scroll="true"
        :data="treeData"
        key-field="id"
        label-field="name"
        :expandedKeys="expandedKeys"
        style="max-height: 650px; overflow: hidden"
        @update:selected-keys="selectedTree"
      />
      <template #footer>
        <n-space>
          <n-button type="primary" :loading="subLoading" @click="formSubmit">提交</n-button>
          <n-button @click="handleReset">重置</n-button>
        </n-space>
      </template>
    </n-drawer-content>
  </n-drawer>
</template>

<script lang="ts">
  import { defineComponent, reactive, ref, toRefs ,onMounted} from 'vue';
  import { useMessage } from 'naive-ui';
  import { getMenuList } from '@/api/system/menu';
  

  export default defineComponent({
    name: 'ResourceDrawer',
    components: {},
    props: {
      title: {
        type: String,
        default: '添加权限',
      },
      width: {
        type: Number,
        default: 450,
      },
    },
      
    setup() {
      
      const message = useMessage();
      let expandedKeys = ref([]);
      const treeData = ref([]);
      
      const state = reactive({
        isDrawer: false,
        subLoading: false,
        placement: 'right',
      });
      
      new Promise((resolve, reject) => {
        getMenuList()
          .then((res) => {
            treeData.value = res;
            resolve(res);
          })
          .catch((error) => {
            reject(error);
          });
      });
      
      function selectedTree(keys) {
        if (keys.length) {
          const treeItem = getTreeItem(unref(treeData), keys[0]);
          treeItemKey.value = keys;
          treeItemTitle.value = treeItem.label;
          Object.assign(formParams, treeItem);
          isEditMenu.value = true;
        } else {
          isEditMenu.value = false;
          treeItemKey.value = [];
          treeItemTitle.value = '';
        }
      }
      
      function onExpandedKeys(keys) {
        expandedKeys.value = keys;
      }
  
      function openDrawer() {
        state.isDrawer = true;
      }

      function closeDrawer() {
        state.isDrawer = false;
      }

      function formSubmit() {
        
      }

      function handleReset() {}

      return {
        ...toRefs(state),
        treeData,
        formSubmit,
        selectedTree,
        onExpandedKeys,
        handleReset,
        openDrawer,
        closeDrawer,
      };
    },
  });
    
</script>
