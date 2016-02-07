using System;
using System.Data.SqlClient;
using System.Drawing;
using System.IO;
using System.Windows.Forms;
using org.opencv.core;

namespace AuthenticatingU
{
    public partial class MainPage : Form
    {
        public static string webpage = "";
        private string name;
        private string userName;
        private string userPassword;
        private Image userImage;
        public static  SqlConnection connection = new SqlConnection(Properties.Settings.Default.AnuragDbConnectionString);
        public MainPage()
        {
            InitializeComponent();
        }

        private void MainPage_Load(object sender, EventArgs e)
        {
            
        }

        private void btn_addFbUser_Click(object sender, EventArgs e)
        {
            userName = tb_userName.Text;
            userPassword = tb_password.Text;
            name = tb_Name.Text;
            StoreUserData();
        }

        private void StoreUserData()
        {
            connection.Open();
            
            
            

            SqlCommand addUserCommand = new SqlCommand();
            addUserCommand.Connection = connection;
            addUserCommand.CommandText = "INSERT INTO dbo.UserData(NameOfUser,Image) values(@NameOfUser,@Image)";
            addUserCommand.Parameters.AddWithValue("@NameOfUser", name);
            addUserCommand.Parameters.AddWithValue("@Image",userImage);
            addUserCommand.ExecuteNonQuery();
            connection.Close();
            int idCreated = RetrieveId();

        }

        private string EncryptPassword()
        {
            string encrypted = "";
            foreach (char c in userPassword)
            {
                encrypted = encrypted + (char)(c + 1);
            }

            return encrypted;
        }

        

        public byte[] imageToByteArray(System.Drawing.Image imageIn)
        {
            MemoryStream ms = new MemoryStream();
            imageIn.Save(ms, System.Drawing.Imaging.ImageFormat.Gif);
            return ms.ToArray();
        }

        public Image byteArrayToImage(byte[] byteArrayIn)
        {
            MemoryStream ms = new MemoryStream(byteArrayIn);
            Image returnImage = Image.FromStream(ms);
            return returnImage;
        }

        private void StoreUserCredentials(int id,string website)
        {

            connection.Open();
            SqlCommand addUserCredentialsCommand = new SqlCommand();
            addUserCredentialsCommand.Connection = connection;
            addUserCredentialsCommand.CommandText = "INSERT INTO dbo.UserCredentials(Website,UserName,Password,UserData_Id) VALUES(@Website,@UserName,@Password,@UserData_Id)";
            addUserCredentialsCommand.Parameters.AddWithValue("@Website", website);
            addUserCredentialsCommand.Parameters.AddWithValue("@UserName", userName);
            addUserCredentialsCommand.Parameters.AddWithValue("@Password", EncryptPassword());
            addUserCredentialsCommand.Parameters.AddWithValue("@UserData_Id", id);
            addUserCredentialsCommand.ExecuteNonQuery();
            connection.Close();
        }

        private int RetrieveId()
        {
            int id = 0;

            connection.Open();
            SqlCommand getCreatedIdCommand = new SqlCommand();
            getCreatedIdCommand.Connection = connection;
            getCreatedIdCommand.CommandText = "SELECT MAX(Id) FROM dbo.UserData";
            id = (int)getCreatedIdCommand.ExecuteScalar();
            connection.Close();
            return id;
        }

        private void btn_facebook_Click(object sender, EventArgs e)
        {
           
           // string[] args = new string[0];
           // hackathon.Hackathon.main(args);
            WebPageForm webPage = new WebPageForm();
            webpage = "www.fb.com";
            webPage.Show();
            this.Hide();
            
        }
    }
}
