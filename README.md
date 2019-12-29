# QuickAdapter
 简易使用的RecyclerViewAdapter 带有Header和Footer
 
## Converter
### ContentConvert
    adapter.setOnContentConvertListener(new QuickAdapter.OnContentConvertListener<String>() {
        @Override
        public void onContentConvert(QuickAdapter.ContentViewHolder contentViewHolder, String item) {
            Log.d("QuickAdapter","content convert");
        }
    });
     
### HeaderConvert
    adapter.setOnHeaderConvertListener(new QuickAdapter.OnHeaderConvertListener() {
        @Override
        public void onHeaderConvert(QuickAdapter.HeaderViewHolder headerViewHolder) {
            Log.d("QuickAdapter","header convert");
        }
    });
### FooterConvert 
    adapter.setOnFooterConvertListener(new QuickAdapter.OnFooterConvertListener() {
        @Override
        public void onFooterConvert(QuickAdapter.FooterViewHolder footerViewHolder) {
            Log.d("QuickAdapter","footer convert");
        }
    });
## Click
### ItemClick
    adapter.setOnItemClickListener(new QuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(QuickAdapter quickAdapter, View view, int position) {
            Log.d("QuickAdapter", "item click : " + position);
        }
    });
### HeaderClick
    adapter.setOnHeaderClickListener(new QuickAdapter.OnHeaderClickListener() {
         @Override
         public void onHeaderClick(QuickAdapter quickAdapter, View view, int position) {
             Log.d("QuickAdapter", "header click: " + position);
         }
    });
### FooterClick
    adapter.setOnFooterClickListener(new QuickAdapter.OnFooterClickListener() {
        @Override
        public void onFooterClick(QuickAdapter quickAdapter, View view, int position) {
            Log.d("QuickAdapter", "footer click: " + position);
        }
    });
## LongClick
### ItemLongClick
    adapter.setOnItemLongClickListener(new QuickAdapter.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(QuickAdapter quickAdapter, View view, int position) {
            Log.d("QuickAdapter", "item long click : " + position);
            return true;
        }
    });
### HeaderLongClick
    adapter.setOnHeaderLongClickListener(new QuickAdapter.OnHeaderLongClickListener() {
        @Override
        public boolean onHeaderLongClick(QuickAdapter quickAdapter, View view, int position) {
            Log.d("QuickAdapter", "header long click : " + position);
            return true;
        }
    });
### FooterLongClick
    adapter.setOnFooterLongClickListener(new QuickAdapter.OnFooterLongClickListener() {
        @Override
        public boolean onFooterLongClick(QuickAdapter quickAdapter, View view, int position) {
            Log.d("QuickAdapter", "footer long click : " + position);
            return true;
        }
    });
## setAdapter
    RecyclerView rv = findViewById(R.id.rv_content);
    //QuickAdapter<String> adapter = new QuickAdapter(R.layout.item_img);
    //QuickAdapter<String> adapter = new QuickAdapter(R.layout.item_img,new ArrayList());
    //QuickAdapter<String> adapter = new QuickAdapter(R.layout.item_img,R.layout.header_add,R.layout.footer_add,new ArrayList());
    //QuickAdapter<String> adapter = new QuickAdapter(R.layout.item_img,R.layout.header_add,R.layout.footer_add,new ArrayList());
    ......初始化
    rv.setAdapter(adapter);
## Header
    adapter.setHeaderView(R.layout.header_add);
    adapter.removeHeaderView();
## Footer
    adapter.setFooterView(R.layout.footer_add);
    adapter.removeFooterView();
## Content
    adapter.setData(position,t);
    adapter.setNewData(collection);
    adapter.addData(collection);
    adapter.adData(position,collection);
    adapter.addData(t);
    adapter.addData(position,t);
    List<T> data = adapter.getData();
    T item = adapter.getItem(position);
    adapter.remove(positon);
    int itemCount = adapter.getItemCount(); // footerCount + headerCount + contentCount;
    int headerCount = adapter.getHeaderCount();
    int footerCount = adapter.getFooterCount();
    int contentCount = adapter.getContentCount();
