<template></template>
<script lang="ts">
  import { useMessage, useNotification, useDialog } from 'naive-ui';
  // import UiNaive from '@fast-crud/ui-naive';
  import { uiContext } from '@fast-crud/fast-crud';
  export default {
    name: 'MessageContent',
    setup() {
      //挂载在 window 方便与在js中使用
      window['$message'] = useMessage();
      // add by fast-curd, 初始化ui，必须写在setup里面,获取message实例
      // UiNaive.init();

      //TODO production模式下UiNaive.init()居然获取不到message，也是醉了，改成手动加
      const message = useMessage();
      const notification = useNotification();
      const messageBox = useDialog();
      uiContext.ref.init({ message, notification, messageBox });
    },
  };
</script>
