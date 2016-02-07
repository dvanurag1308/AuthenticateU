using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthenticatingU
{
    public partial class WebPageForm : Form
    {
        static int count=0;
        private Image compareImage;
        public WebPageForm()
        {
            InitializeComponent();
        }

        private void WebPageForm_Load(object sender, EventArgs e)
        {
            wb_navigateSite.DocumentCompleted += new WebBrowserDocumentCompletedEventHandler(wb_navigateSite_DocumentCompleted);
            wb_navigateSite.Navigate(MainPage.webpage);
        }

        private void wb_navigateSite_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            WebBrowser fb = sender as WebBrowser;
            count++;
            DataTable userData = GetIdAndImage();
            DataRow matchingRow = null;
            foreach(DataRow row in userData.Rows)
            {
                byte[] image =(byte[]) row[2];
                if (compareImage.Equals(byteArrayToImage(image)))
                {
                    matchingRow = row;
                    break;
                }
            }

            int rowId = (int)matchingRow[0];

            SqlCommand retrieveCredentialsCommand = new SqlCommand();
            MainPage.connection.Open();
            retrieveCredentialsCommand.Connection = MainPage.connection;
            retrieveCredentialsCommand.CommandText = "SELECT UserName,Password FROM dbo.UserCredentials WHERE Website=@Website AND UserData_Id=@UserData_Id";
            retrieveCredentialsCommand.Parameters.AddWithValue("@Website",MainPage.webpage);
            retrieveCredentialsCommand.Parameters.AddWithValue("@UserData_Id",rowId);
            DataTable finalTable = new DataTable();
            finalTable.Load(retrieveCredentialsCommand.ExecuteReader());


            if (count <= 1 && fb.Document.GetElementById("email") != null)
            {
                fb.Document.GetElementById("email").SetAttribute("value", finalTable.Rows[0][0].ToString());
                fb.Document.GetElementById("pass").SetAttribute("value", DecryptPassword(finalTable.Rows[0][1].ToString()));
                fb.Document.GetElementById("u_0_n").InvokeMember("click");
            }
        }


        public Image byteArrayToImage(byte[] byteArrayIn)
        {
            MemoryStream ms = new MemoryStream(byteArrayIn);
            Image returnImage = Image.FromStream(ms);
            return returnImage;
        }

        private string DecryptPassword(String userPassword)
        {
            string decrypted = "";
            foreach (char c in userPassword)
            {
                decrypted = decrypted + (char)(c - 1);
            }

            return decrypted;
        }

        private DataTable GetIdAndImage()
        {
            DataTable dt = new DataTable();
            SqlCommand retrieveImagesCommand = new SqlCommand();
            MainPage.connection.Open();
            retrieveImagesCommand.Connection = MainPage.connection;
            retrieveImagesCommand.CommandText = "SELECT * from dbo.UserData";
            dt.Load(retrieveImagesCommand.ExecuteReader());
            MainPage.connection.Close();
            return dt;
        }
    }
}
