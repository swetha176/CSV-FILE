public partial class _Default : System.Web.UI.Page  {
        SqlConnection con;
   string sqlconn;
    protected void Page_Load(object sender, EventArgs e)
    {
    }
    private void connection()
    {
        sqlconn = ConfigurationManager.ConnectionStrings["SqlCom"].ConnectionString;
        con = new SqlConnection(sqlconn);
    }
    protected void Button1_Click(object sender, EventArgs e)
    {

        DataTable tblcsv = new DataTable();
        tblcsv.Columns.Add("Name");
        tblcsv.Columns.Add("City");
        tblcsv.Columns.Add("Address");
        tblcsv.Columns.Add("Designation");
          string CSVFilePath = Path.GetFullPath(FileUpload1.PostedFile.FileName);
         string ReadCSV = File.ReadAllText(CSVFilePath);
        foreach (string csvRow in ReadCSV.Split('\n'))
        {
            if (!string.IsNullOrEmpty(csvRow))
        tblcsv.Rows.Add();
int count = 0;
                foreach (string FileRec in csvRow.Split(','))
                {
                    tblcsv.Rows[tblcsv.Rows.Count - 1][count] = FileRec;
                    count++;
                }
            }
        }
       InsertCSVRecords(tblcsv);
    }
   private void InsertCSVRecords(DataTable csvdt)
    {
connection();
          SqlCopy obj = new SqlBulkCopy(con);
         obj.DestinationTableName = "Employee";
         obj.ColumnMappings.Add("Name", "Name");
        obj.ColumnMappings.Add("City", "City");
        obj.ColumnMappings.Add("Address", "Address");
        obj.ColumnMappings.Add("Designation", "Designation");
        con.Open();
        objbulk.WriteToServer(csvdt);
 con.Close();

    }
}