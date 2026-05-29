implements Serializable {
    private int page;
    private int total;
    private long records;
    private List<?> rows;

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getRecords() {
        return this.records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public List<?> getRows() {
        return this.rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
