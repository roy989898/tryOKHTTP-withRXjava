package poly.pom.tryokhttpwithrxjava.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import poly.pom.tryokhttpwithrxjava.R;
import poly.pom.tryokhttpwithrxjava.widget.Ratio;

/**
 * Created by Roy.Leung on 23/1/17.
 */

public class RatioAdapter extends BaseAdapter {
    private List<Ratio> ratioList;
    private Context mContext;

    public RatioAdapter(Context context, List<Ratio> objects) {
        this.ratioList = objects;
        this.mContext = context;
    }

    public void setRatioList(List<Ratio> ratioList) {
        this.ratioList = ratioList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ratio ratio = ratioList.get(position);
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ratio_adapter_view, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.ration.setText(ratio.ratio);
        vh.tvCountry.setText(ratio.name);


        return convertView;
    }


    @Override
    public int getCount() {
        if (ratioList != null)
            return ratioList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        if (ratioList != null)
            return ratioList.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        @BindView(R.id.tv_country)
        TextView tvCountry;
        @BindView(R.id.ration)
        TextView ration;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
