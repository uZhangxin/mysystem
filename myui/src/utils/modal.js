import {Message, MessageBox, Notification} from 'element-ui'
import {CODE} from "./code";

export function getModal(code, type, content) {
    // 返回状态码为200
    debugger
    if (code == CODE.HTTP_CODE.SUCCESS) {
        if (type == CODE.MODAL_TYPE.MESSAGE) {
            return Message.success(content)
        }
        if (type == CODE.MODAL_TYPE.MESSAGE_BOX) {
            return MessageBox.alert(content, "系统提示", {type: 'success'})
        }
        if (type == CODE.MODAL_TYPE.NOTIFICATION) {
            return Notification.success(content)
        }
    }
    // 返回状态码为401
    if (code == CODE.HTTP_CODE.UNAUTHORIZED) {
        if (type == CODE.MODAL_TYPE.MESSAGE) {
            return Message.error(content)
        }
        if (type == CODE.MODAL_TYPE.MESSAGE_BOX) {
            return MessageBox.alert(content, "系统提示", {type: 'error'})
        }
        if (type == CODE.MODAL_TYPE.NOTIFICATION) {
            return Notification.error(content)
        }
    }
    // 返回状态码为500
    if (code == CODE.HTTP_CODE.ERROR) {
        if (type == CODE.CODE.MODAL_TYPE.MESSAGE) {
            return Message.warning(content)
        }
        if (type == CODE.MODAL_TYPE.MESSAGE_BOX) {
            return MessageBox.alert(content, "系统提示", {type: 'warning'})
        }
        if (type == CODE.MODAL_TYPE.NOTIFICATION) {
            return Notification.warning(content)
        }
    }
}