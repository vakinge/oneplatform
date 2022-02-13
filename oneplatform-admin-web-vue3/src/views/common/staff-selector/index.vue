<template>
  <div>
    <basicModal @register="modalRegister" ref="modalRef" class="basicModal" @on-ok="okModal">
      <template #default>
        
      </template>
    </basicModal>
  </div>
</template>

<script lang="ts">
  import { defineComponent, ref, reactive, toRefs } from 'vue';
  import { useMessage } from 'naive-ui';
  import { basicModal, useModal } from '@/components/Modal';

  export default defineComponent({
    components: { basicModal, BasicForm },
    setup() {
      const modalRef: any = ref(null);
      const message = useMessage();

      const [modalRegister, { openModal, closeModal, setSubLoading }] = useModal({
        title: '新增预约',
      });

      const [register, { submit }] = useForm({
        gridProps: { cols: 1 },
        collapsedRows: 3,
        labelWidth: 120,
        layout: 'horizontal',
        submitButtonText: '提交预约',
        showActionButtonGroup: false,
        schemas,
      });

      async function okModal() {
        const formRes = await submit();
        if (formRes) {
          closeModal();
          message.success('提交成功');
        } else {
          message.error('验证失败，请填写完整信息');
          setSubLoading(false);
        }
      }

      function showModal() {
        openModal();
      }

      function handleReset(values: Recordable) {
        console.log(values);
      }

      return {
        modalRef,
        register,
        modalRegister,
        handleReset,
        showModal,
        okModal,
      };
    },
  });
</script>

<style lang="less">
  .n-dialog.basicModal {
    width: 640px;
  }
</style>
