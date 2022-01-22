<template>
  <div>
    <div class="n-layout-page-header">
    </div>
    <n-grid class="mt-4" cols="1 s:1 m:1 l:3 xl:3 2xl:3" responsive="screen" :x-gap="12">
      <n-gi span="1">
        <n-card :segmented="{ content: 'hard' }" :bordered="false" size="small">
          <template #header>
            <n-space>
              <n-dropdown trigger="hover" @select="selectAddMenu" :options="addMenuOptions">
                <n-button type="info" ghost icon-placement="right">
                  添加菜单
                  <template #icon>
                    <div class="flex items-center">
                      <n-icon size="14">
                        <DownOutlined />
                      </n-icon>
                    </div>
                  </template>
                </n-button>
              </n-dropdown>
            </n-space>
          </template>
          <div class="w-full menu">
            <n-input type="input" v-model:value="pattern" placeholder="输入菜单名称搜索">
              <template #suffix>
                <n-icon size="18" class="cursor-pointer">
                  <SearchOutlined />
                </n-icon>
              </template>
            </n-input>
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
                  checkable
                  :virtual-scroll="true"
                  :pattern="pattern"
                  :data="treeData"
                  key-field="id"
                  label-field="name"
                  :expandedKeys="expandedKeys"
                  style="max-height: 650px; overflow: hidden"
                  @update:selected-keys="selectedTree"
                  @update:expanded-keys="onExpandedKeys"
                />
              </template>
            </div>
          </div>
        </n-card>
      </n-gi>
      <n-gi span="2">
        <n-card :segmented="{ content: 'hard' }" :bordered="false" size="small">
          <template #header>
            <n-space>
              <n-icon size="18">
                <FormOutlined />
              </n-icon>
              <span>编辑菜单{{ treeItemTitle ? `：${treeItemTitle}` : '' }}</span>
            </n-space>
          </template>
          <n-alert type="info" closable> 从菜单列表选择一项后，进行编辑</n-alert>
          <n-form
            :model="formParams"
            :rules="rules"
            ref="formRef"
            label-placement="left"
            :label-width="100"
            v-if="isEditMenu"
            class="py-4"
          >
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
            <n-form-item style="margin-left: 100px">
              <n-space>
                <n-button :disabled="formParams.isDefault" type="primary" :loading="subLoading" @click="formSubmit"
                  >保存修改</n-button
                >
                <n-button @click="handleReset">重置</n-button>
              </n-space>
            </n-form-item>
          </n-form>
        </n-card>
      </n-gi>
    </n-grid>
    <CreateDrawer ref="createDrawerRef" />
  </div>
</template>
<script lang="ts" setup>
  import { ref, unref, reactive, onMounted, computed } from 'vue';
  import { useMessage } from 'naive-ui';
  import { DownOutlined, AlignLeftOutlined, SearchOutlined, FormOutlined } from '@vicons/antd';
  import { getMenuList,updateMenu } from '@/api/system/menu';
  import { getTreeItem } from '@/utils';
  import CreateDrawer from './CreateDrawer.vue';

  const rules = {
    name: {
      required: true,
      message: '请输入标题',
      trigger: 'blur',
    },
    router: {
      required: true,
      message: '请输入路径',
      trigger: 'blur',
    },
  };

  const formRef: any = ref(null);
  const createDrawerRef = ref();
  const message = useMessage();
  
  let selectTreeItem = ref({});

  let expandedKeys = ref([]);

  const treeData = ref([]);

  const loading = ref(true);
  const subLoading = ref(false);
  const isEditMenu = ref(false);
  const pattern = ref('');

  const isDisabledMenu = computed(() => {
    return !selectTreeItem || selectTreeItem.type !== 'catalog';
  });
  
  const isDisabledButton = computed(() => {
    return !selectTreeItem || selectTreeItem.type !== 'menu';
  });

  const addMenuOptions = ref([
    {
      label: '添加菜单目录',
      key: 'catalog',
      disabled: false,
    },
    {
      label: '添加菜单项',
      key: 'menu',
      disabled: false,
    },
    {
      label: '添加按钮',
      key: 'button',
      disabled: false,
    },
  ]);

  const formParams = reactive({});

  function selectAddMenu(key: string) {
    if(key === 'catalog' && selectTreeItem.type !=='catalog'){
      message.error('菜单目录才能添加目录');
      return;
    }
    if(key === 'menu' && selectTreeItem.type !=='catalog'){
      message.error('菜单目录才能添加子菜单');
      return;
    }
    if(key === 'button' && selectTreeItem.type !=='menu'){
      message.error('子菜单下才能创建按钮');
      return;
    }
    const { openDrawer } = createDrawerRef.value;
    openDrawer(key,selectTreeItem);
  }

  function selectedTree(keys) {
    if (keys.length) {
      const item = getTreeItem(unref(treeData), keys[0]);
      selectTreeItem = item;
      //delete selectTreeItem.children;
      Object.assign(formParams, selectTreeItem);
      isEditMenu.value = true;
    } else {
      isEditMenu.value = false;
    }
  }

  function handleReset() {
    Object.assign(formParams, selectTreeItem);
  }

  function formSubmit() {
    formRef.value.validate(async (errors: boolean) => {
      if (!errors) {
        await updateMenu(formParams);
        message.success('保存成功');
      } else {
        message.error('请填写完整信息');
      }
    });
  }

  onMounted(async () => {
    const treeMenuList = await getMenuList();
    treeData.value = treeMenuList;
    loading.value = false;
  });

  function onExpandedKeys(keys) {
    expandedKeys.value = keys;
  }
</script>
