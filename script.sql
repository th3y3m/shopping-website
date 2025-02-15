USE [master]
GO
/****** Object:  Database [PizzaStore]    Script Date: 3/15/2024 5:23:42 PM ******/
CREATE DATABASE [PizzaStore]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'PizzaStore', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL11.SQLEXPRESS\MSSQL\DATA\PizzaStore.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'PizzaStore_log', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL11.SQLEXPRESS\MSSQL\DATA\PizzaStore_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [PizzaStore] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [PizzaStore].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [PizzaStore] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [PizzaStore] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [PizzaStore] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [PizzaStore] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [PizzaStore] SET ARITHABORT OFF 
GO
ALTER DATABASE [PizzaStore] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [PizzaStore] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [PizzaStore] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [PizzaStore] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [PizzaStore] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [PizzaStore] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [PizzaStore] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [PizzaStore] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [PizzaStore] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [PizzaStore] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [PizzaStore] SET  DISABLE_BROKER 
GO
ALTER DATABASE [PizzaStore] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [PizzaStore] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [PizzaStore] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [PizzaStore] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [PizzaStore] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [PizzaStore] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [PizzaStore] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [PizzaStore] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [PizzaStore] SET  MULTI_USER 
GO
ALTER DATABASE [PizzaStore] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [PizzaStore] SET DB_CHAINING OFF 
GO
ALTER DATABASE [PizzaStore] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [PizzaStore] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [PizzaStore]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 3/15/2024 5:23:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Account](
	[AccountID] [varchar](10) NOT NULL,
	[UserName] [varchar](20) NULL,
	[Password] [varchar](20) NULL,
	[FullName] [varchar](20) NULL,
	[Type] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[AccountID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 3/15/2024 5:23:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Categories](
	[CategoryID] [varchar](10) NOT NULL,
	[CategoryName] [varchar](20) NULL,
	[Description] [varchar](250) NULL,
PRIMARY KEY CLUSTERED 
(
	[CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Customers]    Script Date: 3/15/2024 5:23:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Customers](
	[CustomerID] [varchar](10) NOT NULL,
	[Password] [varchar](20) NULL,
	[ContactName] [varchar](50) NULL,
	[Address] [varchar](250) NULL,
	[Phone] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[CustomerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 3/15/2024 5:23:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[OrderDetails](
	[OrderId] [varchar](10) NOT NULL,
	[ProductId] [varchar](10) NOT NULL,
	[UnitPrice] [money] NULL,
	[Quantity] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderId] ASC,
	[ProductId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 3/15/2024 5:23:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Orders](
	[OrderID] [varchar](10) NOT NULL,
	[CustomerID] [varchar](10) NULL,
	[OrderDate] [datetime] NULL,
	[RequiredDate] [datetime] NULL,
	[ShippedDate] [datetime] NULL,
	[Freight] [money] NULL,
	[ShipAddress] [varchar](250) NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Products]    Script Date: 3/15/2024 5:23:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Products](
	[ProductID] [varchar](10) NOT NULL,
	[ProductName] [varchar](20) NULL,
	[SupplierID] [varchar](10) NULL,
	[CategoryID] [varchar](10) NULL,
	[QuantityPerUnit] [int] NULL,
	[UnitPrice] [money] NULL,
	[ProductImage] [varchar](250) NULL,
PRIMARY KEY CLUSTERED 
(
	[ProductID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Suppliers]    Script Date: 3/15/2024 5:23:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Suppliers](
	[SupplierID] [varchar](10) NOT NULL,
	[CompanyName] [varchar](50) NULL,
	[Address] [varchar](250) NULL,
	[Phone] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[SupplierID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[Account] ([AccountID], [UserName], [Password], [FullName], [Type]) VALUES (N'C000', N'A000', N'123', N'Admin', 1)
INSERT [dbo].[Account] ([AccountID], [UserName], [Password], [FullName], [Type]) VALUES (N'C001', N'A001', N'123', N'John Doe', 0)
INSERT [dbo].[Account] ([AccountID], [UserName], [Password], [FullName], [Type]) VALUES (N'C002', N'A002', N'123', N'Jane Smith', 0)
INSERT [dbo].[Account] ([AccountID], [UserName], [Password], [FullName], [Type]) VALUES (N'C003', N'A003', N'123', N'Mark', 0)
INSERT [dbo].[Account] ([AccountID], [UserName], [Password], [FullName], [Type]) VALUES (N'C004', N'A004', N'123', N'Michael Johnson', 0)
INSERT [dbo].[Account] ([AccountID], [UserName], [Password], [FullName], [Type]) VALUES (N'C005', N'A005', N'123', N'Emily Davis', 0)
INSERT [dbo].[Categories] ([CategoryID], [CategoryName], [Description]) VALUES (N'CAT001', N'Vegetarian', N'Pizzas with no meat toppings')
INSERT [dbo].[Categories] ([CategoryID], [CategoryName], [Description]) VALUES (N'CAT002', N'Beef', N'Pizzas loaded with meat toppings')
INSERT [dbo].[Categories] ([CategoryID], [CategoryName], [Description]) VALUES (N'CAT003', N'Seafood', N'Pizzas featuring seafood toppings')
INSERT [dbo].[Categories] ([CategoryID], [CategoryName], [Description]) VALUES (N'CAT004', N'Chicken', N'Pizzas loaded with chicken toppings')
INSERT [dbo].[Categories] ([CategoryID], [CategoryName], [Description]) VALUES (N'CAT005', N'Pork', N'Pizzas loaded with pork toppings')
INSERT [dbo].[Customers] ([CustomerID], [Password], [ContactName], [Address], [Phone]) VALUES (N'C001', N'123', N'John Doe', N'123 Main St, City, Country', N'1234567890')
INSERT [dbo].[Customers] ([CustomerID], [Password], [ContactName], [Address], [Phone]) VALUES (N'C002', N'123', N'Jane Smith', N'456 Oak St, Town, Country', N'9876543210')
INSERT [dbo].[Customers] ([CustomerID], [Password], [ContactName], [Address], [Phone]) VALUES (N'C003', N'123', N'Mark', N'123 Main St, City, Country', N'1234567890')
INSERT [dbo].[Customers] ([CustomerID], [Password], [ContactName], [Address], [Phone]) VALUES (N'C004', N'123', N'Michael Johnson', N'789 Pine St, City, Country', N'3216540987')
INSERT [dbo].[Customers] ([CustomerID], [Password], [ContactName], [Address], [Phone]) VALUES (N'C005', N'123', N'Emily Davis', N'1010 Cedar St, Town, Country', N'5551234567')
INSERT [dbo].[OrderDetails] ([OrderId], [ProductId], [UnitPrice], [Quantity]) VALUES (N'O001', N'P001', 9.9900, 2)
INSERT [dbo].[OrderDetails] ([OrderId], [ProductId], [UnitPrice], [Quantity]) VALUES (N'O001', N'P002', 11.9900, 1)
INSERT [dbo].[OrderDetails] ([OrderId], [ProductId], [UnitPrice], [Quantity]) VALUES (N'O002', N'P004', 14.9900, 3)
INSERT [dbo].[OrderDetails] ([OrderId], [ProductId], [UnitPrice], [Quantity]) VALUES (N'O003', N'P006', 15.9900, 2)
INSERT [dbo].[OrderDetails] ([OrderId], [ProductId], [UnitPrice], [Quantity]) VALUES (N'O004', N'P007', 10.9900, 1)
INSERT [dbo].[Orders] ([OrderID], [CustomerID], [OrderDate], [RequiredDate], [ShippedDate], [Freight], [ShipAddress]) VALUES (N'O001', N'C001', CAST(0x0000B12D009450C0 AS DateTime), CAST(0x0000B13400A4CB80 AS DateTime), CAST(0x0000B13300FF6EA0 AS DateTime), 5.0000, N'123 Main St, City, Country')
INSERT [dbo].[Orders] ([OrderID], [CustomerID], [OrderDate], [RequiredDate], [ShippedDate], [Freight], [ShipAddress]) VALUES (N'O002', N'C002', CAST(0x0000B11000B54640 AS DateTime), CAST(0x0000B11700C5C100 AS DateTime), CAST(0x0000B11800E6B680 AS DateTime), 7.5000, N'456 Oak St, Town, Country')
INSERT [dbo].[Orders] ([OrderID], [CustomerID], [OrderDate], [RequiredDate], [ShippedDate], [Freight], [ShipAddress]) VALUES (N'O003', N'C003', CAST(0x0000B0F100D63BC0 AS DateTime), CAST(0x0000B0F80107AC00 AS DateTime), CAST(0x0000B0F90130DEE0 AS DateTime), 6.5000, N'789 Pine St, City, Country')
INSERT [dbo].[Orders] ([OrderID], [CustomerID], [OrderDate], [RequiredDate], [ShippedDate], [Freight], [ShipAddress]) VALUES (N'O004', N'C004', CAST(0x0000B126009C8E20 AS DateTime), CAST(0x0000B12D011826C0 AS DateTime), CAST(0x0000B12E0155F310 AS DateTime), 8.0000, N'1010 Cedar St, Town, Country')
INSERT [dbo].[Products] ([ProductID], [ProductName], [SupplierID], [CategoryID], [QuantityPerUnit], [UnitPrice], [ProductImage]) VALUES (N'P001', N'OCEAN MANIA', N'S001', N'CAT003', 1, 9.9900, N'image/OCEAN_MANIA.jpg')
INSERT [dbo].[Products] ([ProductID], [ProductName], [SupplierID], [CategoryID], [QuantityPerUnit], [UnitPrice], [ProductImage]) VALUES (N'P002', N'PIZZAMIN SEA', N'S004', N'CAT003', 1, 11.9900, N'image/PIZZAMIN_SEA.jpg')
INSERT [dbo].[Products] ([ProductID], [ProductName], [SupplierID], [CategoryID], [QuantityPerUnit], [UnitPrice], [ProductImage]) VALUES (N'P003', N'SURF & TURF', N'S001', N'CAT003', 1, 12.9900, N'image/SURF&TURF.jpg')
INSERT [dbo].[Products] ([ProductID], [ProductName], [SupplierID], [CategoryID], [QuantityPerUnit], [UnitPrice], [ProductImage]) VALUES (N'P004', N'SEAFOOD DELIGHT', N'S002', N'CAT003', 1, 14.9900, N'image/SEAFOOD_DELIGHT.jpg')
INSERT [dbo].[Products] ([ProductID], [ProductName], [SupplierID], [CategoryID], [QuantityPerUnit], [UnitPrice], [ProductImage]) VALUES (N'P005', N'SEOUL BEEF BULGOGI', N'S002', N'CAT002', 1, 13.9900, N'image/SEOUL_BEEF_BULGOGI.jpg')
INSERT [dbo].[Products] ([ProductID], [ProductName], [SupplierID], [CategoryID], [QuantityPerUnit], [UnitPrice], [ProductImage]) VALUES (N'P006', N'NEW YORK CHEESESTEAK', N'S003', N'CAT002', 1, 14.9900, N'image/NEW_YORK_CHEESESTEAK.jpg')
INSERT [dbo].[Products] ([ProductID], [ProductName], [SupplierID], [CategoryID], [QuantityPerUnit], [UnitPrice], [ProductImage]) VALUES (N'P007', N'EXTRAVAGANZA', N'S003', N'CAT002', 1, 17.9900, N'image/EXTRAVAGANZA.jpg')
INSERT [dbo].[Products] ([ProductID], [ProductName], [SupplierID], [CategoryID], [QuantityPerUnit], [UnitPrice], [ProductImage]) VALUES (N'P008', N'MEAT LOVERS', N'S003', N'CAT002', 1, 14.9900, N'image/MEAT_LOVERS.jpg')
INSERT [dbo].[Products] ([ProductID], [ProductName], [SupplierID], [CategoryID], [QuantityPerUnit], [UnitPrice], [ProductImage]) VALUES (N'P009', N'CHEESY CHICKEN BACON', N'S004', N'CAT004', 1, 20.9900, N'image/CHEESY_CHICKEN_BACON.jpg')
INSERT [dbo].[Products] ([ProductID], [ProductName], [SupplierID], [CategoryID], [QuantityPerUnit], [UnitPrice], [ProductImage]) VALUES (N'P010', N'TERIYAKI CHICKEN', N'S002', N'CAT004', 1, 17.9900, N'image/TERIYAKI_CHICKEN.jpg')
INSERT [dbo].[Products] ([ProductID], [ProductName], [SupplierID], [CategoryID], [QuantityPerUnit], [UnitPrice], [ProductImage]) VALUES (N'P011', N'PEPPERONI FEAST', N'S004', N'CAT005', 1, 17.9900, N'image/PEPPERONI_FEAST.jpg')
INSERT [dbo].[Products] ([ProductID], [ProductName], [SupplierID], [CategoryID], [QuantityPerUnit], [UnitPrice], [ProductImage]) VALUES (N'P012', N'HAWAIIAN', N'S003', N'CAT005', 1, 13.9900, N'image/HAWAIIAN.jpg')
INSERT [dbo].[Products] ([ProductID], [ProductName], [SupplierID], [CategoryID], [QuantityPerUnit], [UnitPrice], [ProductImage]) VALUES (N'P013', N'VEGGIE MANIA', N'S001', N'CAT001', 1, 14.9900, N'image/VEGGIE_MANIA.jpg')
INSERT [dbo].[Products] ([ProductID], [ProductName], [SupplierID], [CategoryID], [QuantityPerUnit], [UnitPrice], [ProductImage]) VALUES (N'P014', N'CHEESE MANIA', N'S002', N'CAT001', 1, 22.9900, N'image/CHEESE_MANIA.jpg')
INSERT [dbo].[Suppliers] ([SupplierID], [CompanyName], [Address], [Phone]) VALUES (N'S001', N'Fresh Ingredients Inc.', N'789 Elm St, City, Country', N'4567890123')
INSERT [dbo].[Suppliers] ([SupplierID], [CompanyName], [Address], [Phone]) VALUES (N'S002', N'Farm to Table Foods', N'321 Maple St, Town, Country', N'7890123456')
INSERT [dbo].[Suppliers] ([SupplierID], [CompanyName], [Address], [Phone]) VALUES (N'S003', N'Local Farms Co-op', N'222 Orchard Ave, City, Country', N'8885551234')
INSERT [dbo].[Suppliers] ([SupplierID], [CompanyName], [Address], [Phone]) VALUES (N'S004', N'Artisan Bakers LLC', N'333 Baker St, Town, Country', N'9997774567')
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__Account__C9F28456B196B622]    Script Date: 3/15/2024 5:23:42 PM ******/
ALTER TABLE [dbo].[Account] ADD UNIQUE NONCLUSTERED 
(
	[UserName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD FOREIGN KEY([OrderId])
REFERENCES [dbo].[Orders] ([OrderID])
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD FOREIGN KEY([ProductId])
REFERENCES [dbo].[Products] ([ProductID])
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD FOREIGN KEY([CustomerID])
REFERENCES [dbo].[Customers] ([CustomerID])
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD FOREIGN KEY([CategoryID])
REFERENCES [dbo].[Categories] ([CategoryID])
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD FOREIGN KEY([SupplierID])
REFERENCES [dbo].[Suppliers] ([SupplierID])
GO
USE [master]
GO
ALTER DATABASE [PizzaStore] SET  READ_WRITE 
GO
