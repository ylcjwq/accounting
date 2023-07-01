import useUserStore from "./user"

export default function useStore() {
    return {
        userInfo: useUserStore()
    }
}