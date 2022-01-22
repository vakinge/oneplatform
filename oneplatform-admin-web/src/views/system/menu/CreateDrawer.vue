<template>
  <n-drawer v-model:show="isDrawer" width="500px" :placement="placement">
    <n-drawer-content title="添加" closable>
      <n-form
        :model="formParams"
        :rules="rules"
        ref="formRef"
        label-placement="left"
        :label-width="100"
      >
        <n-form-item label="类型" path="type">
          <span>{{formParams.type}}</span>
        </n-form-item>
        <n-form-item label="父节点" path="parentId" v-if="formParams.parentId">
          <span>{{formParams.parentName}}</span>
        </n-form-item>
        <n-form-item label="名称" path="name">
          <n-input placeholder="请输入名称" v-model:value="formParams.name" />
        </n-form-item>
        <n-form-item label="路由" path="router">
          <n-input placeholder="请输入路由地址" v-model:value="formParams.router" />
        </n-form-item>
        <n-form-item label="页面路径" path="viewPath" v-if="formParams.type ==='menu'">
          <n-input placeholder="请输入路径" v-model:value="formParams.viewPath" />
        </n-form-item>
        <n-form-item label="适用客户端" path="clientType" v-if="formParams.type ==='catalog'">
          <n-radio-group v-model:value="formParams.clientType" name="clientType">
            <n-space>
              <n-radio key="pc" value="pc">PC端</n-radio>
              <n-radio key="mobile" value="mobile">手机端</n-radio>
            </n-space>
          </n-radio-group>
        </n-form-item>
        <n-form-item label="是否展示" path="isDisplay">
          <n-switch v-model:value="formParams.isDisplay" />
        </n-form-item>
        <n-form-item label="开放权限" path="isOpenAccess">
          <n-switch v-model:value="formParams.isOpenAccess" />
        </n-form-item>
        <n-form-item label="排序" path="sort" v-if="formParams.type !=='button'">
           <n-input-number size="small" v-model:value="formParams.sort" />
        </n-form-item>
      </n-form>

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
  import { defineComponent, reactive, ref, toRefs } from 'vue';
  import { useMessage } from 'naive-ui';
  import { addMenu } from '@/api/system/menu';

  const rules = {
    label: {
      required: true,
      message: '请输入标题',
      trigger: 'blur',
    },
    path: {
      required: true,
      message: '请输入路径',
      trigger: 'blur',
    },
  };
  export default defineComponent({
    name: 'CreateDrawer',
    components: {},
    props: {},
    setup() {
      const message = useMessage();
      const formRef: any = ref(null);
      let menuType = ref('');
      let parentId = ref('');
      let parentName = ref('');
      
      const defaultValueRef = () => ({
        clientType: 'pc',
        isDisplay: true,
        isOpenAccess: false,
      });
      
      const state = reactive({
        isDrawer: false,
        subLoading: false,
        formParams: {
          type: menuType,
          parentId: parentId,
          parentName: parentName,
          clientType: 'pc',
          isDisplay: true,
          isOpenAccess: false,
          sort: 99,
        },
        placement: 'right',
      });
 
      function openDrawer(type,node) {
        state.isDrawer = true;
        menuType.value = type;
        parentId.value = node.id;
        parentName.value = node.name;
      }

      function closeDrawer() {
        state.isDrawer = false;
      }

      function formSubmit() {
        formRef.value.validate(async (errors) => {
          if (!errors) {
            delete state.formParams.children;
            if(formParams.type === 'catalog'){
              delete state.formParams.viewPath;
            }
            await addMenu(state.formParams);
            message.success('添加成功');
            handleReset();
            closeDrawer();
          } else {
            message.error('请填写完整信息');
          }
        });
      }

      function handleReset() {
        formRef.value.restoreValidation();
        state.formParams = Object.assign(state.formParams, defaultValueRef());
      }

      return {
        ...toRefs(state),
        formRef,
        rules,
        formSubmit,
        handleReset,
        openDrawer,
        closeDrawer,
      };
    },
  });
</script>
