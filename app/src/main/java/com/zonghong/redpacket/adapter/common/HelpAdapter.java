package com.zonghong.redpacket.adapter.common;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.ItemBankBinding;
import com.zonghong.redpacket.databinding.ItemCommonTextArrowBinding;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.List;
import java.util.Map;

public class HelpAdapter extends BaseQuickAdapter<Map, BaseViewHolder> {


    public HelpAdapter(@Nullable List<Map> data) {
        super(R.layout.item_common_text_arrow, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Map item) {
        ItemCommonTextArrowBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvTitle.setText((String) item.get("title"));
        binding.getRoot().setOnClickListener((v) -> {
            IntentUtils.intent2TextActivity(item);
        });
    }
}
