package com.waw.hr.mutils.event;

import com.waw.hr.mutils.bean.BankListBean;

public class UserEvent {

    public static class LOGIN_SUC_EVENT {
    }

    public static class CHOOSE_BANK_EVENT {

        private BankListBean bankListBean;

        public CHOOSE_BANK_EVENT(BankListBean bankListBean) {
            this.bankListBean = bankListBean;
        }

        public BankListBean getBankListBean() {
            return bankListBean;
        }
    }


    public static class PAY_SUC_EVENT {

    }

    public static class RECHARGE_SUC_EVENT {

    }

    public static class CASH_SUC_EVENT {

    }

    public static class ZHUANZHUANG_SUC_EVENT {

    }

    public static class SEND_MINGPIAN_EVENT {

    }

    public static class SEND_REDPACKAGE_EVENT {

    }

    public static class LOGOUT_EVENT {

    }


}
